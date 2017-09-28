package com.example.denny.mybluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ServerActivity extends AppCompatActivity{

    public static final String TAG = "BTServer";

    BluetoothManager helper;
    TextView textView;
    EditText editText;

    Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        listen();
    }

    public void reStartClick(View view){
        if(helper != null){
            helper.close();
            helper = null;
        }
        listen();
    }

    public void sendClick(View view){
        if(helper != null){
            String text = editText.getText().toString();
            Log.d(TAG, "sendClick: " + text);
            helper.send(text);
        }
    }

    Handler handler = new Handler();
    public void display(final String s){
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(s);
            }
        });
    }

    public void listen(){
        executor.execute(listenForClient);
    }

    Runnable listenForClient = new Runnable(){

        BluetoothServerSocket mServerSocket;

        @Override
        public void run() {
            Log.d(TAG, "run: Start");
            BluetoothSocket socket = connect();
            handleResult(socket);
            close();
            Log.d(TAG, "run: End");
        }

        BluetoothSocket connect(){
            try {
                mServerSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord(BluetoothConfig.NAME, UUID.fromString(BluetoothConfig.UUID));
                return mServerSocket.accept();
            } catch (IOException e) {
                display("Socket初始化失敗");
                e.printStackTrace();
                return null;
            }
        }

        void close(){
            try {
                Log.d(TAG, "close: ");
                mServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void handleResult(BluetoothSocket socket){
            Log.d(TAG, "handleResult: ");
            if(socket == null){
                return;
            }

            try{
                helper = new BluetoothManager(socket);
                display("Start Connect");
                executor.execute(continuousDisplayMessage);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    };


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
