package com.smartway.e_canteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.smartway.e_canteen.Common.Common;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {
    FButton mMainEaterBtn, mMainServerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!Common.isConnectedToInternet(getBaseContext())) {
            Toast.makeText(this, "Check Your Internet Connection!!!", Toast.LENGTH_SHORT).show();
        } else {
            mMainEaterBtn = (FButton) findViewById(R.id.mainEaterBtn);
            mMainServerBtn = (FButton) findViewById(R.id.mainServerBtn);
            mMainEaterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, EaterMainActivity.class));
                }
            });

            mMainServerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ServerMainActivity.class));
                }
            });

        }
    }

}
