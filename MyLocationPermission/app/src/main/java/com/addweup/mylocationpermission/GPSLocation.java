package com.addweup.mylocationpermission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by cdfq1 on 2017/2/28.
 */

public class GPSLocation {
    private static final int LOCATION_PERMISSION = 9527;
    Handler handler = new Handler();
    MyApplication application;

    public GPSLocation(MyApplication application){
        this.application = application;
    }

    void start(){
        handler.post(runnable);
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                getCurrentLocation();
                handler.postDelayed(runnable, 5000);
            }
            catch (SecurityException e){
                e.printStackTrace();
            }
        }
    };

    void getCurrentLocation() throws SecurityException{
        LocationManager locationManager = (LocationManager) application.getSystemService(LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if(location != null){
            Log.i("Location", String.format("Longtitude: %f\nLatitude: %f", location.getLongitude(), location.getLatitude()));
        }
        else{
            Log.i("Location", "No location");
        }
    }
}
