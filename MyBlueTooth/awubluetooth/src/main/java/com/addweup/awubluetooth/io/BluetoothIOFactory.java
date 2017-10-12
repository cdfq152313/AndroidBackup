package com.addweup.awubluetooth.io;

import android.bluetooth.BluetoothDevice;

import com.addweup.awubluetooth.io.BluetoothIO;

/**
 * Created by denny on 2017/10/6.
 */

public class BluetoothIOFactory {
    private BluetoothIOFactory(){

    }

    public static BluetoothIO client(BluetoothDevice bluetoothDevice, String uuid){
        return new BluetoothIOImpl(bluetoothDevice, uuid);
    }

    public static BluetoothIO server(String uuid){
        return new BluetoothIOImpl(uuid);
    }
}
