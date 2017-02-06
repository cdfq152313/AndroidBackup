package com.addweup.lifecycleapp;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by cdfq1 on 2016/12/21.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected String getTAG(){
        return TAG;
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(getTAG(), "OnStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(getTAG(), "OnStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getTAG(), "OnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(getTAG(), "OnResume");

        if(getDismissable()){
            finishAffinity();
        }
    }

    protected boolean getDismissable(){
        return false;
    }
}