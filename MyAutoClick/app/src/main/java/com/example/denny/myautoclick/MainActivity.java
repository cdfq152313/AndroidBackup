package com.example.denny.myautoclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Process p;
    DataOutputStream stdin;
    InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init(){
        try {
            p = Runtime.getRuntime().exec(new String[]{"su", "-c", "system/bin/sh"});
            stdin = new DataOutputStream(p.getOutputStream());
            inputStream = p.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void firstClick(View view){
        ADB_Click(382,1160);
    }

    public void secondClick(View view){
        Toast.makeText(this ,"Second Click", Toast.LENGTH_SHORT).show();
    }

    private void ADB_Click(int x, int y){
        try {
            String command = String.format("input tap %d %d\n", x, y);
            stdin.writeBytes(command);
            stdin.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
