package com.addweup.mybroadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver br = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void register(View view){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Hello");
        registerReceiver(br , intentFilter);
    }

    public void unregister(View view){
        unregisterReceiver(br);
    }

    public void sendBroadcast(View view){
        Intent intent = new Intent();
        intent.setAction("Hello");
        sendBroadcast(intent);
    }

    public void notification(View view){
        Notification noti  = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_upload)
                .setContentTitle("Madadabi meow meow")
                .setContentText("Oh my goodness, oh my dame. Oh my goodness~~~ I'v got a ham. You should know that there a man has power of nature.")
                .build();

        // 取得NotificationManager物件
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 使用BASIC_ID為編號發出通知
        manager.notify(3310, noti);
    }
}
