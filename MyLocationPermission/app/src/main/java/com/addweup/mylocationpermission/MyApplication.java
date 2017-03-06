package com.addweup.mylocationpermission;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by cdfq1 on 2017/2/28.
 */

public class MyApplication extends Application {
    Activity activity = null;
    public GPSLocation GPSLocation;
    public Activity getCurrentActivity(){
        return activity;
    }

    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        GPSLocation = new GPSLocation(this);
    };

    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        static final String TAG = "LifeCycle";

        @Override
        public void onActivityResumed(Activity activity) {
            Log.i(TAG, String.format("Resumed: %s", activity.getLocalClassName()));
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.i(TAG, String.format("Paused: %s", activity.getLocalClassName()));
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.i(TAG, String.format("Started: %s", activity.getLocalClassName()));
            MyApplication.this.activity = activity;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.i(TAG, String.format("Stopped: %s", activity.getLocalClassName()));
            if(MyApplication.this.activity == activity){
                MyApplication.this.activity = null;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG, String.format("Destroyed: %s", activity.getLocalClassName()));
            if(MyApplication.this.activity == activity){
                MyApplication.this.activity = null;
            }
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i(TAG, String.format("Created: %s", activity.getLocalClassName()));
        }
    };
}
