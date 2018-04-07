package com.smartway.e_canteen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartway.e_canteen.Common.Common;
import com.smartway.e_canteen.Common.ServerCommon;
import com.smartway.e_canteen.Model.ServerUser;

import io.paperdb.Paper;

public class ServerMainActivity extends AppCompatActivity {
    Button mSignInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_main);
        mSignInBtn = (Button) findViewById(R.id.signInBtn);
        Paper.init(this);
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServerMainActivity.this, ServerSignIn.class));
            }
        });
        String user = Paper.book().read(ServerCommon.USER_KEY);
        String pwd = Paper.book().read(ServerCommon.PWD_KEY);
        if (user != null && pwd != null){
            if (!user.isEmpty() && !pwd.isEmpty()){
                login(user,pwd);
            }
        }
    }

    private void login(String phone, String pwd){
        final DatabaseReference users = FirebaseDatabase.getInstance().getReference("User");
        final ProgressDialog mDialog = new ProgressDialog(ServerMainActivity.this);
        mDialog.setMessage("Signing In...");
        mDialog.show();
        final String localPhone = phone;
        final String localPassword = pwd;
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(localPhone).exists()){
                    mDialog.dismiss();
                    ServerUser user = dataSnapshot.child(localPhone).getValue(ServerUser.class);
                    user.setPhone(localPhone);
                    if (Boolean.parseBoolean(user.getIsStaff())){
                        if(user.getPassword().equals(localPassword)){
                            mDialog.dismiss();
                            Toast.makeText(ServerMainActivity.this, "Sign In Successful!!!", Toast.LENGTH_SHORT).show();
                            ServerCommon.currentUser = user;
                            startActivity(new Intent(ServerMainActivity.this, ServerHome.class));
                            finish();
                        }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(ServerMainActivity.this, "Wrong Password!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        mDialog.dismiss();
                        Toast.makeText(ServerMainActivity.this, "Not Registered as Server!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(ServerMainActivity.this, "User Doesn't Exists!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
