package com.addweup.awubluetooth.communicator;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by denny on 2017/10/6.
 */

public class BluetoothCommunicator implements BluetoothCommunicatorInterface{

    private static final String TAG = "BTCommunicator";
    private static final String SENDTAG = TAG +"SEND";
    private static final String RECVTAG = TAG +"RECV";

    BluetoothSocket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    CommunicateListener listener;

    HandlerThread sendHandlerThread;
    HandlerThread recvHandlerThread;

    Handler sendHandler;
    Handler recvHandler;

    boolean closing = false;

    public BluetoothCommunicator(BluetoothSocket socket, CommunicateListener listener) throws IOException {
        this.socket = socket;
        this.listener = listener;

        sendHandlerThread = new HandlerThread(SENDTAG);
        recvHandlerThread = new HandlerThread(RECVTAG);

        sendHandler = new Handler(sendHandlerThread.getLooper());
        recvHandler = new Handler(recvHandlerThread.getLooper());

        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream());

        socket.connect();
        startReceive();
    }

    @Override
    public void send(final String object){
        sendHandler.post(new Runnable() {
            @Override
            public void run() {
                printWriter.println(object);
                printWriter.flush();
            }
        });
    }

    private void startReceive(){
        recvHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String response = bufferedReader.readLine();
                        listener.onResponse(response);
                    }
                } catch (IOException e) {
                    if(!closing){
                        listener.onDisconnect();
                        close();
                    }
                }
            }
        });
    }

    public void close(){
        closing = true;
        try {
            printWriter.close();
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendHandlerThread.quit();
        recvHandlerThread.quit();
    }
}