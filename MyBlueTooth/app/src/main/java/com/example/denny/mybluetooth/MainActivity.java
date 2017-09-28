package com.example.denny.mybluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public static final int REQUEST_ENABLE_BT = 2988;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
    }

    public void serverClick(View view){
        Intent intent = new Intent(this, ServerActivity.class);
        startActivity(intent);
    }

    public void clientClick(View view){
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    public void checkPermission(){
        if(mBluetoothAdapter != null){
            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

}
