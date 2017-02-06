package com.addweup.lifecycleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecondAcitivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_acitivity);
    }

    @Override
    protected String getTAG(){
        return "Second";
    }

    public void thirdClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, ThirdActivity.class);
        startActivity(intent);
    }
}
