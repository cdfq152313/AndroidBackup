package com.example.denny.mytextrecognition.google;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.denny.mytextrecognition.R;
import com.example.denny.mytextrecognition.google.ui.CameraPreview;
import com.example.denny.mytextrecognition.google.ui.TextTracker;

public class GoogleVisionActivity extends AppCompatActivity {

    TextView display;
    CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_vision);

        checkPermission();

        display = (TextView) findViewById(R.id.display);
        cameraPreview = (CameraPreview) findViewById(R.id.camera_preview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(hasPermission()){
            cameraPreview.setListener(updateTextView);
            cameraPreview.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(hasPermission()){
            cameraPreview.pause();
        }
    }

    TextTracker.Listener updateTextView = new TextTracker.Listener() {
        @Override
        public void onUpdate(final String qrcode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    display.setText(qrcode);
                }
            });
        }
    };

    public void checkPermission(){
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    9547);
        }
    }

    public boolean hasPermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }
}
