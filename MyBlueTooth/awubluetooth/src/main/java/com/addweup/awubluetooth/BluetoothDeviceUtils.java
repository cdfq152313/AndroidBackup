package com.addweup.awubluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by denny on 2017/10/6.
 */

public class BluetoothDeviceUtils {
    private BluetoothDeviceUtils(){

    }

    public static void listSearchingDevice(){
        // TODO: 應該要一個Callback，因為會持續搜尋
    }

    public static Set<BluetoothDevice> listPairDevices(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        return pairedDevices;
    }

    public static List<String> listPairDevicesNameString(Set<BluetoothDevice> pairedDevices){
        List<String> s = new ArrayList<>();
        for(BluetoothDevice bt : pairedDevices){
            s.add(bt.getName());
        }
        return s;
    }
}
