package com.example.denny.mybluetooth.service;

import android.Manifest;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class BluetoothClientService extends Service implements BluetoothClientServiceInterface {

    public BluetoothClientService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        currentState = disconnectedState;
        bluetooth = new Bluetooth(bluetoothCallback);
    }

    Bluetooth.Callback bluetoothCallback = new Bluetooth.Callback() {
        @Override
        public void onResponse(String response) {
            if(clientCallback != null){
                clientCallback.onResponse(response);
            }
        }

        @Override
        public void onDeviceFail() {
            // TODO: Tell host
            clientCallback.statusChanged(INVALIDDEVICE);
        }

        @Override
        public void onConnected() {
            // change state
            currentState = connectedState;
            // if there are message in queue, send it
            if(messageQueue != null){
                currentState.send(messageQueue);
                messageQueue = null;
            }
        }

        @Override
        public void onDisconnected() {
            // change state
            currentState = disconnectedState;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return new BluetoothServiceBinder();
    }

    public class BluetoothServiceBinder extends Binder {
        public BluetoothClientServiceInterface getService(){
            return BluetoothClientService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bluetooth.disconnect();
    }

    Bluetooth bluetooth;
    State currentState;
    ConnectedState connectedState = new ConnectedState();
    DisconnectedState disconnectedState = new DisconnectedState();
    String messageQueue; // only queue one message

    interface State{
        void send(String s);
    }

    class ConnectedState implements State{
        @Override
        public void send(String s) {
            bluetooth.send(s);
        }
    }

    class DisconnectedState implements State{
        @Override
        public void send(String s) {
            messageQueue = s;
        }
    }

    // =====
    // Client Callback
    // =====

    Callback clientCallback;

    @Override
    public void setCallback(Callback callback){
        this.clientCallback = callback;
        notifyStatus();
    }

    void notifyStatus(){
        // check callback
        if(clientCallback == null){
            return;
        }

        // no permission
        boolean permission = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_PRIVILEGED) == PERMISSION_GRANTED;
        if(!permission){
            clientCallback.statusChanged(NOPERMISSION);
            return;
        }

        // disable
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(!adapter.isEnabled()){
            clientCallback.statusChanged(DISABLE);
            return;
        }
        // disconnected
        if(currentState == disconnectedState){
            clientCallback.statusChanged(DISCONNECTED);
            return;
        }
        // connected
        if(currentState == connectedState){
            clientCallback.statusChanged(CONNECTED);
            return;
        }
        // unknown
        clientCallback.statusChanged(UNKNOWN);
    }
}
