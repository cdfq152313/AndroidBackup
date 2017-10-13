package com.addweup.awubluetooth.io;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.addweup.awubluetooth.communicator.BluetoothCommunicator;
import com.addweup.awubluetooth.communicator.BluetoothCommunicatorInterface;
import com.addweup.awubluetooth.connector.BluetoothConnectorClient;
import com.addweup.awubluetooth.connector.BluetoothConnectorInterface;
import com.addweup.awubluetooth.connector.BluetoothConnectorServer;

import java.io.IOException;

/**
 * Created by denny on 2017/10/6.
 */

public class BluetoothIOImpl implements BluetoothIO, BluetoothCommunicatorInterface.CommunicateListener, BluetoothConnectorInterface.ConnectListener{

    BluetoothConnectorInterface bluetoothConnector;
    BluetoothCommunicatorInterface bluetoothCommunicator;

    BluetoothIO.Listener listener;

    public BluetoothIOImpl(BluetoothDevice bluetoothDevice, String uuid){
        bluetoothConnector = new BluetoothConnectorClient(bluetoothDevice, uuid, this);
    }

    public BluetoothIOImpl(String uuid){
        bluetoothConnector = new BluetoothConnectorServer(uuid, this);
    }

    @Override
    public void connect(){
        // 開始連線
        bluetoothConnector.connect();
    }

    @Override
    public void disconnect(){
        // 停止連線並釋放所有資源
        bluetoothConnector.disconnect();
        if(bluetoothCommunicator != null){
            bluetoothCommunicator.close();
            bluetoothCommunicator = null;
        }
    }

    public void setListener(BluetoothIO.Listener listener){
        this.listener = listener;
    }

    @Override
    public void send(String object){
        if(bluetoothCommunicator != null){
            bluetoothCommunicator.send(object);
        }
    }

    @Override
    public void onConnect(BluetoothSocket socket) {
        try{
            bluetoothCommunicator = new BluetoothCommunicator(socket, this);
            if(listener != null){
                listener.onConnect();
            }
        }catch (IOException e){
            bluetoothCommunicator = null;
            bluetoothConnector.connect();
        }
    }

    @Override
    public void onResponse(String response) {
        if(listener != null){
            listener.onResponse(response);
        }
    }

    @Override
    public void onDisconnect() {
        // 斷線會自動重連
        if(listener != null){
            listener.onDisconnect();
        }
        bluetoothCommunicator = null;
        bluetoothConnector.connect();
    }
}
