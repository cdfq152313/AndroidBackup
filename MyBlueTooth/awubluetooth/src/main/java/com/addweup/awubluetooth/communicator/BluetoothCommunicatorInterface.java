package com.addweup.awubluetooth.communicator;

/**
 * Created by denny on 2017/10/6.
 */

public interface BluetoothCommunicatorInterface {
    void send(String object);
    void close();

    interface CommunicateListener{
        void onResponse(String response);
        void onDisconnect();
    }
}
