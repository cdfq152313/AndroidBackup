package com.example.denny.mybarcode2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.denny.mybarcode2.google.GoogleBarcodeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void googleClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, GoogleBarcodeActivity.class);
        startActivity(intent);
    }
}
