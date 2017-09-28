package com.example.denny.mybluetooth.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.HandlerThread;


import com.example.denny.mybluetooth.BluetoothConfig;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by denny on 2017/8/22.
 */

public class BluetoothConnector implements Runnable {

    BluetoothDevice device;

    HandlerThread handlerThread;
    Handler handler;
    Callback callback;

    BluetoothConnector(BluetoothDevice bluetoothDevice, Callback callback){
        device = bluetoothDevice;
        this.callback = callback;

        handlerThread = new HandlerThread("com.addweup.uchange2.service.BluetoothConnector");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    BluetoothConnector(Callback callback) throws BluetoothException.NoDefaultDevice {
        device = initDevice();
        this.callback = callback;

        handlerThread = new HandlerThread("com.addweup.uchange2.service.BluetoothConnector");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    BluetoothDevice initDevice() throws BluetoothException.NoDefaultDevice {
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                return device;
            }
        }
        throw new BluetoothException.NoDefaultDevice();
    }

    public void start(){
        handler.post(this);
    }

    @Override
    public void run() {
        // retry until connect
        try{
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(UUID.fromString(BluetoothConfig.UUID));
            socket.connect();
            BluetoothCommunicator communicator = new BluetoothCommunicator(socket);
            callback.onConnected(communicator);
        }catch (IOException e){
            e.printStackTrace();
            handler.postDelayed(this, 1000);
        }
    }

    public void close(){
        handlerThread.quit();
    }

    interface Callback{
        void onConnected(BluetoothCommunicator communicator);
    }
}
