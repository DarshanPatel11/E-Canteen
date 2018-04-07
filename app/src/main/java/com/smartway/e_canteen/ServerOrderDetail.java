package com.smartway.e_canteen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.smartway.e_canteen.Common.ServerCommon;
import com.smartway.e_canteen.ViewHolder.ServerOrderDetailAdapter;

public class ServerOrderDetail extends AppCompatActivity {
    TextView order_id, order_phone, order_address, order_total, order_comment;
    String order_id_value="";
    RecyclerView lstFoods;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_order_detail);
        order_id = (TextView)findViewById(R.id.order_id);
        order_phone = (TextView)findViewById(R.id.order_phone);
        order_address = (TextView)findViewById(R.id.order_address);
        order_total = (TextView)findViewById(R.id.order_total);
        order_comment = (TextView)findViewById(R.id.order_comment);
        lstFoods = (RecyclerView)findViewById(R.id.lstFoods);
        lstFoods.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstFoods.setLayoutManager(layoutManager);
        if (getIntent().getStringExtra("OrderId") !=null){
            order_id_value = getIntent().getStringExtra("OrderId");
        }
        order_id.setText(order_id_value);
        order_phone.setText(ServerCommon.currentRequests.getPhone());
        order_total.setText(ServerCommon.currentRequests.getTotal());
        order_address.setText(ServerCommon.currentRequests.getAddress());
        order_comment.setText(ServerCommon.currentRequests.getComment());
        ServerOrderDetailAdapter adapter = new ServerOrderDetailAdapter(ServerCommon.currentRequests.getFoods());
        adapter.notifyDataSetChanged();
        lstFoods.setAdapter(adapter);
    }
}
