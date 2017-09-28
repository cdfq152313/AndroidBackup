package com.example.denny.mybluetooth.service;

/**
 * Created by denny on 2017/8/22.
 */

public interface BluetoothClientServiceInterface {
    int NOPERMISSION = 1;
    int DISABLE = 2;
    int CONNECTED = 3;
    int DISCONNECTED = 4;
    int INVALIDDEVICE = 5;
    int UNKNOWN = 20;

    interface Callback{
        void statusChanged(int status);
        void onResponse(String code);
    }

    void setCallback(Callback callback);
}
