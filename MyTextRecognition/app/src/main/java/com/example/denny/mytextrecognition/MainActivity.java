package com.example.denny.mytextrecognition;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.denny.mytextrecognition.cameraview.CameraViewActivity;
import com.example.denny.mytextrecognition.google.GoogleVisionActivity;
import com.example.denny.mytextrecognition.microblink.MicroBlinkActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    public void checkPermission(){
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    9547);
        }
    }

    public boolean hasPermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void googleClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, GoogleVisionActivity.class);
        startActivity(intent);
    }

    public void cameraViewClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, CameraViewActivity.class);
        startActivity(intent);
    }

    public void microBlinkClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, MicroBlinkActivity.class);
        startActivity(intent);
    }
}
