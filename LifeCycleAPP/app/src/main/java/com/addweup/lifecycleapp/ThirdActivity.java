package com.addweup.lifecycleapp;

import android.os.Bundle;
import android.view.View;

public class ThirdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    @Override
    protected String getTAG(){
        return "Third";
    }

    public void dissmissClick(View view){
        finishAffinity();
    }

}
