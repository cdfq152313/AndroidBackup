package com.addweup.mybarcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.addweup.mybarcode.zxingtest.ZXingCustomizeActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void googleBarcodeClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, GoogleBarcodeActivity.class);
        startActivity(intent);
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

    public void zxingCustomizedClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, ZXingCustomizeActivity.class);
        startActivity(intent);
    }

    public void genQRCodeClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, GenQRCodeActivity.class);
        startActivity(intent);
    }
}
