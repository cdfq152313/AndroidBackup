package com.example.denny.myclickable;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    BlankFragment fragment;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.status);
        initFragment();
    }

    private void initFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment = new BlankFragment();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();
    }

    public void onClick(View view){
        fragment.enableClick();
    }

    public void offClick(View view){
        fragment.disableClick();
    }

    public void offActClick(View view){
        status.setText("Activity Disable");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                status.setText("Clickable");
            }
        }, 3000);
    }

    public void oneClick(View view){
        Toast.makeText(this, "Activity Click", Toast.LENGTH_SHORT).show();
    }
}
