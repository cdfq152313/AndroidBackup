package com.example.denny.mybarcode2.google.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * Created by denny on 2017/5/4.
 */

public class BarcodeScanner extends FrameLayout {
    static final String TAG = "BarcodeScanner";

    SurfaceView mSurfaceView;
    CameraSource mCameraSource;
    BarcodeDetector mBarcodeDetector;
    boolean mStartRequested;
    boolean mSurfaceAvailable;

    CameraHelper.Size size;

    public BarcodeScanner(@NonNull Context context) {
        super(context);
        initialize();
    }

    public BarcodeScanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        size = CameraHelper.getInstance().getAvailableSize();
        Log.i(TAG, String.format("Width: %d, Height: %d", size.width, size.height));

        mStartRequested = false;
        mSurfaceAvailable = false;
        mBarcodeDetector = createBarcodeDetector();
        mCameraSource = createCameraSource(size.width, size.height, true, mBarcodeDetector);
        mSurfaceView = createSurfaceView();
    }

    public void resume(){
        if(mCameraSource != null){
            mStartRequested = true;
            startPreviewIfReady();
        }
    }

    public void pause(){
        if(mCameraSource != null){
            mCameraSource.stop();
        }
    }

    public void destroy(){
        if(mCameraSource != null){
            mCameraSource.release();
        }
    }

    public void setListener(BarcodeTracker.Listener listener){
        BarcodeTracker tracker = new BarcodeTracker(listener);
        mBarcodeDetector.setProcessor(new MultiProcessor.Builder<>(tracker).build());
    }

    void startPreviewIfReady(){
        try {
            if (mStartRequested && mSurfaceAvailable) {
                mCameraSource.start(mSurfaceView.getHolder());
                mStartRequested = false;
            }
        } catch (IOException e) {
            Log.e(TAG, "Cannot open camera source.", e);
        } catch (SecurityException e){
            Log.e(TAG, "Camera permission deny.", e);
        }
    }

    SurfaceView createSurfaceView(){
        SurfaceView surfaceView = new CameraPreview(getContext(), size.width, size.height);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceAvailable = true;
                startPreviewIfReady();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceAvailable = false;
            }
        });

        addView(surfaceView);
        return surfaceView;
    }

    BarcodeDetector createBarcodeDetector(){
        Context context = getContext().getApplicationContext();
        BarcodeDetector.Builder builder = new BarcodeDetector.Builder(context);
//        builder.setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE | Barcode.AZTEC | Barcode.PDF417);
        return builder.build();
    }

    CameraSource createCameraSource(int width, int height, boolean autoFocus, BarcodeDetector detector){
        Context context = getContext().getApplicationContext();
        CameraSource.Builder builder = new CameraSource.Builder(context, detector)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedPreviewSize(width, height)
                .setRequestedFps(15.0f)
                .setAutoFocusEnabled(autoFocus);
        return builder.build();
    }

    CameraSource createCameraSource(boolean autoFocus, BarcodeDetector detector){
        Context context = getContext().getApplicationContext();
        CameraSource.Builder builder = new CameraSource.Builder(context, detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(15.0f)
                .setAutoFocusEnabled(autoFocus);
        return builder.build();
    }
}
