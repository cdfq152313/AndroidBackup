package com.addweup.mylocationpermission;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

/**
 * Created by cdfq1 on 2017/2/28.
 */

public class BaseActivity extends AppCompatActivity {
    private static final int PERMISSION = 9527;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(needLocation()){
            checkLocationPermission();
        }
        if(needCamera()){
            checkCameraPermission();
        }
    }

    protected boolean needLocation(){
        return true;
    }
    protected boolean needCamera(){
        return true;
    }

    protected void checkLocationPermission(){
        int permission = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{ACCESS_FINE_LOCATION},
                    PERMISSION
            );
        }
    }

    protected void checkCameraPermission(){
        int permission = ActivityCompat.checkSelfPermission(this, CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{CAMERA},
                    PERMISSION
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

