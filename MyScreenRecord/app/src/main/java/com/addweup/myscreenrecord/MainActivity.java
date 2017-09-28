package com.addweup.myscreenrecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.addweup.myscreenrecord.camera.CameraActivity;
import com.addweup.myscreenrecord.camera2.Camera2Activity;
import com.addweup.myscreenrecord.projection.MediaProjectionActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mediaProjectionClick(View view){
        Intent intent = new Intent(this, MediaProjectionActivity.class);
        startActivity(intent);
    }

    public void cameraClick(View view){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void camera2Click(View view){
        Intent intent = new Intent(this, Camera2Activity.class);
        startActivity(intent);
    }
}
