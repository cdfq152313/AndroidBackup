package com.example.denny.mybluetooth;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.addweup.awubluetooth.io.BluetoothIO;
import com.addweup.awubluetooth.io.BluetoothIOFactory;


public class ServerActivity extends AppCompatActivity implements BluetoothIO.Listener{

    public static final String TAG = "BTServer";

    TextView textView;
    EditText editText;

    BluetoothIO bluetoothIO;

    Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);

        bluetoothIO = BluetoothIOFactory.server(BluetoothConfig.UUID);
        bluetoothIO.setListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bluetoothIO.connect();
        display("Connecting");
    }

    @Override
    protected void onStop() {
        super.onStop();
        bluetoothIO.disconnect();
    }

    public void sendClick(View view){
        if(bluetoothIO == null){
            return;
        }
        bluetoothIO.send(editText.getText().toString());
    }

    @Override
    public void onConnect() {
        display("Connected");
    }

    @Override
    public void onResponse(String response) {
        display(response);
    }

    @Override
    public void onDisconnect() {
        display("Disconnected");
    }

    private void display(final String text){
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }
}
