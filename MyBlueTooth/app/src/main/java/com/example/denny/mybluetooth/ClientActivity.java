package com.example.denny.mybluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.addweup.awubluetooth.BluetoothDeviceUtils;
import com.addweup.awubluetooth.io.BluetoothIO;
import com.addweup.awubluetooth.io.BluetoothIOFactory;

import java.util.Set;

public class ClientActivity extends AppCompatActivity implements BluetoothIO.Listener {

    public static final String TAG = "BTClient";

    TextView textView;
    EditText editText;
    ListView listView;

    Handler uiHandler = new Handler();

    BluetoothIO bluetoothIO;
    Set<BluetoothDevice> pairDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        pairDevices = BluetoothDeviceUtils.listPairDevices();
        initView();
    }

    private void initView(){
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, BluetoothDeviceUtils.listPairDevicesNameString(pairDevices)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String deviceName = (String) adapterView.getItemAtPosition(i);
                for(BluetoothDevice device:pairDevices){
                    if(device.getName().equals(deviceName)){
                        if(bluetoothIO != null) bluetoothIO.disconnect();
                        bluetoothIO = BluetoothIOFactory.client(device, BluetoothConfig.UUID);
                        bluetoothIO.connect();
                        bluetoothIO.setListener(ClientActivity.this);
                        display("Connecting");
                        return;
                    }
                }
                Toast.makeText(ClientActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bluetoothIO != null){
            bluetoothIO.disconnect();
        }
    }

    public void display(final String s){
        Log.d(TAG, "display: " + s);
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(s);
            }
        });
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
}
