package com.example.denny.myimage.dialog;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.denny.myimage.R;

public class DialogZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_zoom);
    }

    public void dialogClick(View view){
        DialogFragment dialog = new ImageDialogFragment();
        dialog.show(getFragmentManager(), null);
    }
}
