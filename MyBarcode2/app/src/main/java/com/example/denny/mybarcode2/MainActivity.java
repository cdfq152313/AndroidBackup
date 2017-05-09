package com.example.denny.mybarcode2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.denny.mybarcode2.google.GoogleBarcodeActivity;
import com.example.denny.mybarcode2.zxingJourneyapps.ZXingJourneyAppsActivity;
import com.example.denny.mybarcode2.zxingOrig.ZXingOriginActivity;

public class MainActivity extends AppCompatActivity {

    static final int PERMISSION = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requirePermission();
    }

    public void googleClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, GoogleBarcodeActivity.class);
        startActivity(intent);
    }

    public void zxingJourneyClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, ZXingJourneyAppsActivity.class);
        startActivity(intent);
    }

    public void zxingOriginClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, ZXingOriginActivity.class);
        startActivity(intent);
    }

    public void requirePermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
