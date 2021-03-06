package com.addweup.awubluetooth.io;

/**
 * Created by denny on 2017/10/6.
 */

public interface BluetoothIO{
    void connect();
    void disconnect();
    void setListener(Listener listener);
    void send(String object);

    interface Listener{
        void onConnect();
        void onResponse(String response);
        void onDisconnect();
    }
}
