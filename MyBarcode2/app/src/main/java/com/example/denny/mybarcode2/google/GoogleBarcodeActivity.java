package com.example.denny.mybarcode2.google;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denny.mybarcode2.R;
import com.example.denny.mybarcode2.google.ui.BarcodeTracker;
import com.example.denny.mybarcode2.google.ui.CameraSourcePreview;

import java.io.IOException;

public class GoogleBarcodeActivity extends AppCompatActivity {
    static final String TAG = "GVisionAct";

    Handler uiHandler = new Handler();
    TextView textView;
    CameraSourcePreview preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_barcode);
        textView = (TextView) findViewById(R.id.display);
        preview = (CameraSourcePreview) findViewById(R.id.preview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            preview.setListener(new BarcodeTracker.Listener() {
                @Override
                public void onUpdate(String qrcode) {
                    displayText(qrcode);
                }
            });
            preview.start();
        }catch (IOException e){
            Log.e(TAG, "Could not start camera source.", e);
        }catch (SecurityException e){
            Log.e(TAG, "Do not have permission to start the camera", e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (preview != null) {
            preview.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preview != null) {
            preview.release();
        }
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
