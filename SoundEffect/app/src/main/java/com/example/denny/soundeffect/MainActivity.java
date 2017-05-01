package com.example.denny.soundeffect;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View view){
        ring(RingtoneManager.TYPE_NOTIFICATION);
    }

    public void click2(View view){
        ring(RingtoneManager.TYPE_ALARM);
    }

    public void click3(View view){
        ring(RingtoneManager.TYPE_ALL);
    }

    public void click4(View view){
        ring(RingtoneManager.TYPE_RINGTONE);
    }

    public void ring(int type){
        try {
            Uri notification = RingtoneManager.getDefaultUri(type);
            final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    r.stop();
                }
            }, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
