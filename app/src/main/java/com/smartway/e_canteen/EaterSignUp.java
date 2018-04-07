package com.smartway.e_canteen;

import android.app.ProgressDialog;
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
import com.smartway.e_canteen.Model.User;

public class EaterSignUp extends AppCompatActivity {
    MaterialEditText mEdtName,mEdtPhone, mEdtPassword;
    Button mSignUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eater_sign_up);
        mEdtName = (MaterialEditText) findViewById(R.id.edtName);
        mEdtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        mEdtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        mSignUpBtn = (Button) findViewById(R.id.signInBtn);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(EaterSignUp.this);
                mDialog.setMessage("Signing In...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(mEdtPhone.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(EaterSignUp.this, "User Already Registered!!!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mDialog.dismiss();
                            User user = new User(mEdtName.getText().toString(), mEdtPassword.getText().toString());
                            table_user.child(mEdtPhone.getText().toString()).setValue(user);
                            Toast.makeText(EaterSignUp.this, "Sign Up Successful!!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mDialog.dismiss();
                        Toast.makeText(EaterSignUp.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
