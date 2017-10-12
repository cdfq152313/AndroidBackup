package com.addweup.awubluetooth.connector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by denny on 2017/10/6.
 */

public class BluetoothConnectorClient implements BluetoothConnectorInterface, Runnable{
    private static final String TAG = "BTConnectorClient";

    BluetoothDevice device;
    String uuid;
    ConnectListener listener;
    HandlerThread handlerThread;
    Handler handler;

    public BluetoothConnectorClient(BluetoothDevice device, String uuid, ConnectListener listener){
        this.device = device;
        this.uuid = uuid;
        this.listener = listener;

        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    @Override
    public void connect() {
        handler.post(this);
    }

    @Override
    public void disconnect() {
        handler.removeCallbacks(this);
        handlerThread.quit();
    }

    @Override
    public void run() {
        if(!BluetoothAdapter.getDefaultAdapter().isEnabled()){
            handler.postDelayed(this, RETRY_TIME);
        }

        // retry until connect
        try{
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));
            socket.connect();
            listener.onConnect(socket);
        }catch (IOException e){
            e.printStackTrace();
            handler.postDelayed(this, RETRY_TIME);
        }
    }
}
