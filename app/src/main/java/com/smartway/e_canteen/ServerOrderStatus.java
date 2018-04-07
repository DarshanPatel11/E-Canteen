package com.smartway.e_canteen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.smartway.e_canteen.Common.Common;
import com.smartway.e_canteen.Common.ServerCommon;
import com.smartway.e_canteen.Interface.ItemClickListener;
import com.smartway.e_canteen.Model.MyResponse;
import com.smartway.e_canteen.Model.Notification;
import com.smartway.e_canteen.Model.Order;
import com.smartway.e_canteen.Model.Request;
import com.smartway.e_canteen.Model.Sender;
import com.smartway.e_canteen.Model.Token;
import com.smartway.e_canteen.Remote.APIService;
import com.smartway.e_canteen.ViewHolder.ServerOrderViewHolder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerOrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    MaterialSpinner spinner;
    FirebaseRecyclerAdapter<Request, ServerOrderViewHolder> adapter;
    APIService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        mService = ServerCommon.getFCMService();
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadOrders();
    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, ServerOrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                ServerOrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(ServerOrderViewHolder viewHolder, final Request model, int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(ServerCommon.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClicked) {
                        if (!isLongClicked){
                            Intent orderDetail = new Intent(ServerOrderStatus.this, ServerOrderDetail.class);
                            ServerCommon.currentRequests = model;
                            orderDetail.putExtra("OrderId",adapter.getRef(position).getKey());
                            startActivity(orderDetail);
                        }
                        /*else {
                        Intent trackingOrder = new Intent(ServerOrderStatus.this, ServerOrderDetail.class);
                            ServerCommon.currentRequests = model;
                            startActivity(trackingOrder);

                        } */
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(ServerCommon.UPDATE)){
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        }
        else if (item.getTitle().equals(ServerCommon.DELETE)){
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(String key, final Request item) {
        final AlertDialog.Builder alrtDialog = new AlertDialog.Builder(ServerOrderStatus.this);
        alrtDialog.setTitle("Update Order Status");
        alrtDialog.setMessage("Select status to update.");
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.server_update_order_layout, null);
        spinner = view.findViewById(R.id.statusSpinner);
        spinner.setItems("Placed", "Preparing", "Delivered");
        alrtDialog.setView(view);
        final String localKey = key;
        alrtDialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));
                requests.child(localKey).setValue(item);
                sendOrderStatusToUser(localKey,item);
            }
        });
        alrtDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alrtDialog.show();
    }

    private void sendOrderStatusToUser(final String key ,final Request item) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        tokens.orderByKey().equalTo(item.getPhone())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Token token = postSnapshot.getValue(Token.class);
                            Notification notification = new Notification("E-Canteen","Status of your order #"+key+" is updated.");
                            Sender content = new Sender(token.getToken(), notification);
                            mService.sendNotification(content)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            if (response.body().success == 1){
                                                Toast.makeText(ServerOrderStatus.this, "Order Status Updated!!!", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(ServerOrderStatus.this, "Failed to send Notification!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {
                                            Log.e("ERROR", t.getMessage());

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();
    }
}
