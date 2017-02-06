package com.addweup.mybarcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void microBlinckClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, MicroBlinkActivity.class);
        startActivity(intent);
    }

    public void zxingClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, ZXingActivity.class);
        startActivity(intent);
    }
}
