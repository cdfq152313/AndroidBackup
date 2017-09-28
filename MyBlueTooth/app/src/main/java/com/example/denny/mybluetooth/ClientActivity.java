package com.example.denny.mybluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class ClientActivity extends AppCompatActivity implements Runnable {

    public static final String TAG = "BTClient";

    TextView textView;
    EditText editText;

    HandlerThread handlerThread;
    Handler bgHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);

        handlerThread = new HandlerThread("client");
        handlerThread.start();
        bgHandler = new Handler(handlerThread.getLooper());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        bgHandler.post(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    Handler uiHandler = new Handler();
    public void display(final String s){
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(s);
            }
        });
    }

    public void reconnectClick(View view){
        if(helper != null){
            helper.close();
            helper = null;
        }
        bgHandler.post(this);
    }

    public void sendClick(View view){
        if(helper != null){
            String text = editText.getText().toString();
            Log.d(TAG, "sendClick: " + text);
            helper.send(text);
        }
    }

    BluetoothManager helper;

    @Override
    public void run() {
        Log.d(TAG, "run: ");
        BluetoothDevice device = initDevice();
        if(device == null){
            display("Initial Device Failed");
            return;
        }

        helper = initHelper(device);
        if(helper == null){
            display("Initial Helper Failed");
            return;
        }
        display("Inital Successfully");
        bgHandler.post(continuousDisplayMessage);
    }

    public BluetoothDevice initDevice(){
        Log.d(TAG, "initDevice: ");
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                display(String.format("DeviceName: %s\nDeviceMac: %s", deviceName, deviceHardwareAddress));
                return device;
            }
        }
        return null;
    }

    public BluetoothManager initHelper(final BluetoothDevice device){
        Log.d(TAG, "initHelper: ");
        try {
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(UUID.fromString(BluetoothConfig.UUID));
            socket.connect();
            return new BluetoothManager(socket);
        } catch (IOException e) {
            // REDO after 3 sec
            bgHandler.postDelayed(this, 3000);
            e.printStackTrace();
            return null;
        }
    }

    Runnable continuousDisplayMessage = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "continuousDisplayMessage start");
            while (helper != null){
                display(helper.receive());
            }
            Log.d(TAG, "continuousDisplayMessage end");
        }
    };
}
