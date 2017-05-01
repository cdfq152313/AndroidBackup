package com.example.denny.zxingspeed;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DecoratedBarcodeView barcodeView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcodeView = (DecoratedBarcodeView) findViewById(R.id.decorated_barcodeView);
        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.decodeContinuous(showQRCode);
        barcodeView.resume();
    }

    int count = 0;
    BarcodeCallback showQRCode = new BarcodeCallback() {
        @Override
        public void barcodeResult(final BarcodeResult result) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    barcodeView.setStatusText(result.getText());
                }
            });
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    barcodeView.setStatusText("");
                }
            }, 2000);
            Log.i("SHOW", String.format("%d: %s", count++, result.getText()));
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }
}
