package com.example.denny.mybluetooth.service;

import android.bluetooth.BluetoothDevice;

/**
 * Created by denny on 2017/8/22.
 */

public class Bluetooth {

    BluetoothConnector connector;
    BluetoothCommunicator communicator;

    Callback callback;

    public Bluetooth(Callback callback){
        this.callback = callback;
    }

    public void connect(){
        if(connector == null){
            try {
                connector = new BluetoothConnector(connectedCallback);
                connector.start();
            } catch (BluetoothException.NoDefaultDevice e) {
                // 初始化失敗
                callback.onDeviceFail();
            }
        }
    }

    public void connect(BluetoothDevice device){
        if(connector == null){
            connector = new BluetoothConnector(device, connectedCallback);
            connector.start();
        }
    }

    public void disconnect(){
        if(connector != null){
            connector.close();
            connector = null;
        }
        if(communicator != null){
            communicator.close();
            communicator = null;
        }
    }

    BluetoothConnector.Callback connectedCallback = new BluetoothConnector.Callback() {
        @Override
        public void onConnected(BluetoothCommunicator communicator) {
            callback.onConnected();
            communicator.setListener(communicatorCallback);
            Bluetooth.this.communicator = communicator;
        }
    };

    BluetoothCommunicator.Callback communicatorCallback = new BluetoothCommunicator.Callback() {
        @Override
        public void onResponse(String response) {
            callback.onResponse(response);
        }

        @Override
        public void onDisconnected() {
            callback.onDisconnected();
            connector.start();
        }
    };

    public void send(String message){
        if(communicator != null){
            communicator.send(message);
        }
    }

    public interface Callback{
        void onResponse(String response);
        void onDeviceFail();
        void onConnected();
        void onDisconnected();
    }
}
