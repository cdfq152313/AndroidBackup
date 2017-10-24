package com.example.denny.mybluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.addweup.awubluetooth.io.BluetoothIO;
import com.addweup.awubluetooth.io.BluetoothIOFactory;

import java.util.Set;
import java.util.TreeSet;

public class ClientActivity extends AppCompatActivity implements BluetoothIO.Listener {

    public static final String TAG = "BTClient";

    TextView textView;
    EditText editText;
    ListView pairListView;
    ListView searchListView;
    ArrayAdapter<String> searchAdapter;

    Handler uiHandler = new Handler();

    BluetoothIO bluetoothIO;
    Set<BluetoothDevice> pairDevices;
    Set<BluetoothDevice> searchDevice = new TreeSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        pairDevices = BluetoothDeviceUtils.listPairDevices();
        initView();

        scanBluetooth();
    }

    private void initView(){
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        pairListView = (ListView) findViewById(R.id.pairlistView);
        pairListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, BluetoothDeviceUtils.listPairDevicesNameString(pairDevices)));
        pairListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        searchListView = (ListView) findViewById(R.id.searchlistView);
        searchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        searchListView.setAdapter(searchAdapter);
    }

    private void scanBluetooth(){
        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        BluetoothAdapter.getDefaultAdapter().startDiscovery();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.i(TAG, "onReceive: Start");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.i(TAG, "onReceive: Finish");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "onReceive: " + device.getName());
                searchDevice.add(device);
                searchAdapter.add(device.getName());
                searchAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
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
