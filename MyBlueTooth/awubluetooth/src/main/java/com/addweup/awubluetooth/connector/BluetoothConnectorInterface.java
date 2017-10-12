package com.addweup.awubluetooth.connector;


import android.bluetooth.BluetoothSocket;

/**
 * Created by denny on 2017/10/6.
 */

public interface BluetoothConnectorInterface {
    String NAME = "AWUBluetooth";
    int RETRY_TIME = 1000;

    void connect();
    void disconnect();

    interface ConnectListener{
        void onConnected(BluetoothSocket socket);
    }
}
