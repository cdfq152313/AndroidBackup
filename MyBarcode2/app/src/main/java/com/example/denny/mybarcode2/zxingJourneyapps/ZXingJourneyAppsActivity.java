package com.example.denny.mybarcode2.zxingJourneyapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.denny.mybarcode2.R;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class ZXingJourneyAppsActivity extends AppCompatActivity {

    static final String TAG = "ZXingJour";
    DecoratedBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_journey_apps);

        barcodeView = (DecoratedBarcodeView) findViewById(R.id.preview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.decodeContinuous(callback);
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            barcodeView.setStatusText(result.getText());
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };
}
