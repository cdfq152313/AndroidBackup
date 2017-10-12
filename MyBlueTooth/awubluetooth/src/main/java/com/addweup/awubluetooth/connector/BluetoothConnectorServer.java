package com.addweup.awubluetooth.connector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by denny on 2017/10/6.
 */

public class BluetoothConnectorServer implements BluetoothConnectorInterface, Runnable {
    private static final String TAG = "BTConnectorServer";

    HandlerThread handlerThread;
    Handler handler;

    ConnectListener listener;
    String uuid;

    public BluetoothConnectorServer(String uuid, ConnectListener listener) {
        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        this.uuid = uuid;
        this.listener = listener;
    }

    @Override
    public void connect() {
        handler.post(this);
    }

    @Override
    public void disconnect() {
        handler.removeCallbacks(this);
        handlerThread.quit();
        if(serverSocket != null){
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    BluetoothServerSocket serverSocket = null;

    @Override
    public void run() {
        try {
            startConnect();
        }catch (IOException e){
            e.printStackTrace();
            handler.postDelayed(this, RETRY_TIME);
        }
    }

    void startConnect() throws IOException {
        if (serverSocket == null) {
            serverSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord(BluetoothConnectorInterface.NAME, UUID.fromString(uuid));
        }
        BluetoothSocket socket = serverSocket.accept();
        listener.onConnect(socket);
    }
}


