package com.addweup.myalertdialog;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void alert1Click(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setTitle("Hello")
                .setMessage("World")
                .setPositiveButton("OK", null)
                .setNeutralButton("Middle", null)
                .setNegativeButton("Cancel", null);
        builder.show();
    }

    public void alert2Click(View view){
        new MyDialogFrament().show(getFragmentManager(), "dialog");
    }

    public void cancelClick(View view){
        new MyLifeCycleDialogFragment().show(getFragmentManager(), "dialog");
    }
}
