package com.example.denny.mybluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by denny on 2017/8/14.
 */

public class BluetoothManager {

    BluetoothSocket socket;
    InputStream inputStream;
    OutputStream outputStream;

    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public BluetoothManager(BluetoothSocket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        printWriter = new PrintWriter(outputStream);
    }

    public void send(String s){
        printWriter.println(s);
        printWriter.flush();
    }

    public String receive(){
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void close(){
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
    }
}
