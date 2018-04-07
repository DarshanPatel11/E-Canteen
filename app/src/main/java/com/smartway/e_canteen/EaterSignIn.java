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
import com.smartway.e_canteen.Model.User;

import io.paperdb.Paper;

public class EaterSignIn extends AppCompatActivity {
    MaterialEditText mEdtPhone, mEdtPassword;
    Button mSignInBtn;
    CheckBox ckbRemember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eater_sign_in);

        mEdtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        mEdtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        mSignInBtn = (Button) findViewById(R.id.signInBtn);
        ckbRemember = (CheckBox)findViewById(R.id.ckbRemember);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        Paper.init(this);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ckbRemember.isChecked()){
                    Paper.book().write(Common.USER_KEY, mEdtPhone.getText().toString());
                    Paper.book().write(Common.PWD_KEY, mEdtPassword.getText().toString());
                }

                final ProgressDialog mDialog = new ProgressDialog(EaterSignIn.this);
                mDialog.setMessage("Signing In...");
                mDialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(mEdtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(mEdtPhone.getText().toString()).getValue(User.class);
                            user.setPhone(mEdtPhone.getText().toString());
                            if (user.getPassword().equals(mEdtPassword.getText().toString())) {
                                Toast.makeText(EaterSignIn.this, "Sign In Successful!!!", Toast.LENGTH_SHORT).show();
                                Common.currentUser = user;
                                startActivity(new Intent(EaterSignIn.this, Home.class));
                                finish();
                            } else {
                                Toast.makeText(EaterSignIn.this, "Wrong Password!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            mDialog.dismiss();
                            Toast.makeText(EaterSignIn.this, "User Doesn't Exists!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mDialog.dismiss();
                        Toast.makeText(EaterSignIn.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
