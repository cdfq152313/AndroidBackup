package com.example.denny.mybarcode2.google;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.denny.mybarcode2.R;
import com.example.denny.mybarcode2.google.ui.BarcodeTracker;
import com.example.denny.mybarcode2.google.ui.CameraPreview;

public class GoogleBarcodeActivity extends AppCompatActivity {
    static final String TAG = "GVisionAct";

    Handler uiHandler = new Handler();
    TextView textView;
    CameraPreview preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_barcode);
        textView = (TextView) findViewById(R.id.display);
        preview = (CameraPreview) findViewById(R.id.preview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "OnResume");
        preview.setListener(new BarcodeTracker.Listener() {
            @Override
            public void onUpdate(String qrcode) {
                displayText(qrcode);
            }
        });
        preview.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        preview.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preview.destroy();
    }

    public void displayText(final String text){
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }
}
