package com.example.denny.myimage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.denny.myimage.animation.ZoomActivity;
import com.example.denny.myimage.dialog.DialogZoomActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void zoomClick(View view){
        Intent intent = new Intent(this, ZoomActivity.class);
        startActivity(intent);
    }

    public void dialogZoomClick(View view){
        Intent intent = new Intent(this, DialogZoomActivity.class);
        startActivity(intent);
    }
}
