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
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;
import com.smartway.e_canteen.Common.Common;
import com.smartway.e_canteen.Common.ServerCommon;
import com.smartway.e_canteen.Model.ServerUser;

import io.paperdb.Paper;

public class ServerSignIn extends AppCompatActivity {
    MaterialEditText mEdtPhone, mEdtPassword;
    Button mSignInBtn;
    CheckBox ckbRemember;

    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_sign_in);
        mEdtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        mEdtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        mSignInBtn = (Button) findViewById(R.id.signInBtn);
        ckbRemember = (CheckBox)findViewById(R.id.ckbRemember);
        Paper.init(this);
        users = FirebaseDatabase.getInstance().getReference("User");

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUser(mEdtPhone.getText().toString(), mEdtPassword.getText().toString());
            }
        });

    }

    private void signInUser(final String phone, String password) {
        if(ckbRemember.isChecked()){
            Paper.book().write(ServerCommon.USER_KEY, mEdtPhone.getText().toString());
            Paper.book().write(ServerCommon.PWD_KEY, mEdtPassword.getText().toString());
        }
        final ProgressDialog mDialog = new ProgressDialog(ServerSignIn.this);
        mDialog.setMessage("Signing In...");
        mDialog.show();
        final String localPhone = phone;
        final String localPassword = password;
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
                            Toast.makeText(ServerSignIn.this, "Sign In Successful!!!", Toast.LENGTH_SHORT).show();
                            ServerCommon.currentUser = user;
                            startActivity(new Intent(ServerSignIn.this, ServerHome.class));
                            finish();
                        }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(ServerSignIn.this, "Wrong Password!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        mDialog.dismiss();
                        Toast.makeText(ServerSignIn.this, "Not Registered as Server!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(ServerSignIn.this, "User Doesn't Exists!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
