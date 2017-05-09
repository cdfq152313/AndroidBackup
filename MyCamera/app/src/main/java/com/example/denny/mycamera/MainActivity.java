package com.example.denny.mycamera;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.denny.mycamera.Camera.CameraFragment;
import com.example.denny.mycamera.Display.DisplayFragment;

public class MainActivity extends AppCompatActivity {

    byte [] imageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        showCameraFragment();
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    9487);
        }
    }

    public void showCameraFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, CameraFragment.newInstance(), null);
        transaction.commit();
    }

    public void showDisplayFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, DisplayFragment.newInstance(), null);
        transaction.commit();
    }

    public void setImageResult(byte[] data){
        imageResult = data;
    }

    public byte[] getImageResult(){
        return imageResult;
    }
}
