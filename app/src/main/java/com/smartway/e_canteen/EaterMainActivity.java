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
import com.smartway.e_canteen.Model.User;

import io.paperdb.Paper;

public class EaterMainActivity extends AppCompatActivity {
    Button mSignUpBtn,mSignInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eater_main);
        mSignUpBtn = (Button) findViewById(R.id.signUpBtn);
        mSignInBtn = (Button) findViewById(R.id.signInBtn);
        Paper.init(this);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EaterMainActivity.this, EaterSignIn.class));
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EaterMainActivity.this, EaterSignUp.class));
            }
        });
        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if (user != null && pwd != null){
            if (!user.isEmpty() && !pwd.isEmpty()){
                login(user,pwd);
            }
        }
    }

    private void login(final String phone, final String pwd) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");
        final ProgressDialog mDialog = new ProgressDialog(EaterMainActivity.this);
        mDialog.setMessage("Signing In...");
        mDialog.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()) {
                    mDialog.dismiss();
                    User user = dataSnapshot.child(phone).getValue(User.class);
                    user.setPhone(phone);
                    if (user.getPassword().equals(pwd)) {
                        Toast.makeText(EaterMainActivity.this, "Sign In Successful!!!", Toast.LENGTH_SHORT).show();
                        Common.currentUser = user;
                        startActivity(new Intent(EaterMainActivity.this, Home.class));
                        finish();
                    } else {
                        Toast.makeText(EaterMainActivity.this, "Wrong Password!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(EaterMainActivity.this, "User Doesn't Exists!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mDialog.dismiss();
                Toast.makeText(EaterMainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
