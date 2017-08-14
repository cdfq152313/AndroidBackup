package com.example.denny.mybarcode2;

import android.app.Application;

/**
 * Created by denny on 2017/7/20.
 */

public class GlobalApplication extends Application {
    static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static GlobalApplication getInstance() {
        return instance;
    }
}
