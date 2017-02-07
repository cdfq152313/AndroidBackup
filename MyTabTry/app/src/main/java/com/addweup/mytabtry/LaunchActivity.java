package com.addweup.mytabtry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void tabClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, TabbedActivity.class);
        startActivity(intent);
    }

    public void fragmentClick(View view){
        Intent intent = new Intent();
        intent.setClass(this, FragmentTryActivity.class);
        startActivity(intent);
    }
}
