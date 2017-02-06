package com.addweup.mybarcode.googletest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.addweup.mybarcode.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {
    Camera myCamera;
    TextView textView;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initComponent();
    }

    Executor executor = Executors.newSingleThreadExecutor();

    void initComponent(){
        textView = (TextView) findViewById(R.id.display);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                myCamera = Camera.open();
                myCamera.setPreviewCallback(new Camera.PreviewCallback() {
                    @Override
                    public void onPreviewFrame(byte[] bytes, Camera camera) {
                        handle(bytes, camera);
                    }
                });
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                if(previewing){
                    myCamera.stopPreview();
                    previewing = false;
                }
                try {
                    myCamera.setPreviewDisplay(surfaceHolder);
                    myCamera.startPreview();
                    previewing = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                myCamera.stopPreview();
                myCamera.release();
                myCamera = null;
                previewing = false;
            }
        });
    }

    void handle(final byte[] bytes,final Camera camera){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    handle2(bytes, camera);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void handle2(final byte[] bytes,final Camera camera) throws Exception {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        BarcodeDetector detector = createBarcodeDetector();
        SparseArray<Barcode> barcode = detect(bitmap, detector);
        decode(barcode);
    }

    BarcodeDetector createBarcodeDetector() throws Exception{
        BarcodeDetector detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            throw new Exception("Could not set up the detector!");
        }
        else{
            return detector;
        }
    }

    SparseArray<Barcode> detect(Bitmap bitmap, BarcodeDetector detector){
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);
        return barcodes;
    }

    void decode(SparseArray<Barcode> barcodes){
        final Barcode thisCode = barcodes.valueAt(0);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(thisCode.rawValue);
            }
        });
    }
}
