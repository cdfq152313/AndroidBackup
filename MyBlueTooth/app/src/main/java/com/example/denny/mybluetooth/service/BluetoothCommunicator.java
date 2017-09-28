package com.example.denny.mybluetooth.service;

import android.bluetooth.BluetoothSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by denny on 2017/8/21.
 */

public class BluetoothCommunicator {
    BluetoothSocket socket;

    BufferedReader bufferedReader;
    PrintWriter printWriter;

    Callback callback;
    ExecutorService executor = Executors.newFixedThreadPool(2);

    boolean closing = false;

    public BluetoothCommunicator(BluetoothSocket socket) throws IOException {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream());
    }

    public void send(final String s){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                printWriter.println(s);
                printWriter.flush();
            }
        });
    }

    public void setListener(Callback callback){
        if(this.callback != null){
            this.callback = callback;
        }else{
            this.callback = callback;
            startReceive();
        }
    }

    private void startReceive(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String response = bufferedReader.readLine();
                        callback.onResponse(response);
                    }
                } catch (IOException e) {
                    if(!closing){
                        callback.onDisconnected();
                        close();
                    }
                }
            }
        });
    }

    public void close(){
        closing = true;
        try {
            printWriter.close();
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdownNow();
    }

    interface Callback{
        void onResponse(String response);
        void onDisconnected();
    }
}
