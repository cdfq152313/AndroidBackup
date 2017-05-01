package com.example.denny.layouttest;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void addViewClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        View header = getLayoutInflater().inflate(R.layout.dialog_header, null, false);
        View body = getLayoutInflater().inflate(R.layout.dialog_body, null, false);
        View button = getLayoutInflater().inflate(R.layout.dialog_button, null, false);

        root.addView(header);
        root.addView(body);
        root.addView(button);

        builder.setView(root);
        builder.show();
    }

    public void replaceViewClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_view, null, false);

        View header = getLayoutInflater().inflate(R.layout.dialog_header, null, false);
        View body = getLayoutInflater().inflate(R.layout.dialog_body, null, false);
        View button = getLayoutInflater().inflate(R.layout.dialog_button, null, false);

        root.removeViewAt(0);
        root.addView(header, 0);
        root.removeViewAt(2);
        root.addView(body, 2);
        root.removeViewAt(4);
        root.addView(button, 4);

        builder.setView(root);
        builder.show();
    }

    public void dismissClick(View view){
        MyDialogFragment dialog = new MyDialogFragment();
        dialog.show(getFragmentManager(), null);
    }
}
