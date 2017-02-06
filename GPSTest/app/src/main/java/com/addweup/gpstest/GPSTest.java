package com.addweup.gpstest;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by cdfq1 on 2016/12/23.
 */

public class GPSTest {
    static final String TAG = "GPSTest";
    static final int MAXCOUNT = 2;
    static final int Interval = 8;
    Context context;
    LocationManager locationManager;
    Subscription subscription;
    int count = 0;

    public GPSTest(Context context){
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    public void start(){
        Observable observable = Observable.interval(3, Interval, TimeUnit.SECONDS);
        subscription = observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                        return getCurrentStatus();
                    }
                })
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean currentStatus) {return isCallbackTrigger(currentStatus);
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean trigger) {
                        if(trigger) notifyObserver();
                    }
                });
    }

    public void stop(){
        subscription.unsubscribe();
    }

    private boolean getCurrentStatus(){
        if(locationManager == null){
            return false;
        }
        try{
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if(location != null){
                Log.i(TAG, String.format("Longtitude: %f\nLatitude: %f", location.getLongitude(), location.getLatitude()));
                return true;
            }
            else{
                Log.i(TAG, "No location");
                return false;
            }
        }
        catch (SecurityException e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean isCallbackTrigger(boolean current_status){
        if(current_status){
            count = 0;
            return false;
        }
        else{
            count += 1;
            if (count >= MAXCOUNT){
                return true;
            }
            else{
                return false;
            }
        }
    }

    private void notifyObserver(){
        if(listener != null){
            listener.onLocationError();
        }
    }

    Listener listener = null;
    public void setLocationErrorListener(Listener listener){
        this.listener = listener;
    }
    public void clearListener(){
        this.listener = null;
    }

    public interface Listener{
        void onLocationError();
    }
}
