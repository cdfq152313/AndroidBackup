package com.addweup.myhandler;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyHandler";

    Handler handler;
    Handler handler2;
    ExecutorService executor = Executors.newFixedThreadPool(2);
    HandlerThread handlerThread;
    boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handlerThread = new HandlerThread("com.addweup.myhandler.MainActivity");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        handler2 = new Handler(handlerThread.getLooper());
//        handler.post(task1);
//        handler2.post(task2);
        executor.execute(task1);
        executor.execute(task2);
    }

    Runnable task1 = new Runnable() {
        @Override
        public void run() {
            while (!stop){
                Log.i(TAG, "run1: ");
                sleep();
            }
        }
    };

    Runnable task2 = new Runnable() {
        @Override
        public void run() {
            while (!stop){
                Log.i(TAG, "run2: ");
                sleep();
            }
        }
    };

    public void sleep(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop = true;
        handlerThread.quit();
    }
}
