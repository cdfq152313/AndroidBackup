package com.example.denny.mycamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.denny.mycamera.camera2.Camera2Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    9487);
        }
    }

    public void cameraViewClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, CameraActivity.class);
        intent.putExtra(CameraActivity.INIT_FRAGMENT, CameraActivity.CAMERAVIEW_FRAGMENT);
        startActivity(intent);
    }

    public void cameraClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, CameraActivity.class);
        intent.putExtra(CameraActivity.INIT_FRAGMENT, CameraActivity.CAMERA_FRAGMENT);
        startActivity(intent);
    }

    public void camera2Click(View view){
        Intent intent = new Intent();
        intent.setClass(this, CameraActivity.class);
        intent.putExtra(CameraActivity.INIT_FRAGMENT, CameraActivity.CAMERA2_FRAGMENT);
        startActivity(intent);
    }

}
