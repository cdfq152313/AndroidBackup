package com.addweup.mytabtry.base;

import android.app.Fragment;
import android.util.Log;

/**
 * Created by cdfq1 on 2017/3/9.
 */

public class BaseFragment extends Fragment {

    protected String getClassBaseName(){
        String [] names = this.getClass().getName().split("\\.");
        return names[names.length-1];
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i("onStart", getClassBaseName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("onResume", getClassBaseName());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onPause", getClassBaseName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("onStop", getClassBaseName());
    }

}
