package com.addweup.lifecycleapp;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created by cdfq1 on 2016/12/21.
 */

public class MyApplication extends Application {
    private static final String TAG = "APP";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "OnCreate");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminate");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}