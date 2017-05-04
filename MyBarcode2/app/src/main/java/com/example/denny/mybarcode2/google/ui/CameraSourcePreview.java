package com.example.denny.mybarcode2.google.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * Created by denny on 2017/5/3.
 */

public class CameraSourcePreview extends SurfaceView {
    static final String TAG = "GVision";

    CameraSource mCameraSource;
    BarcodeDetector mBarcodeDetector;

    private boolean mStartRequested;
    private boolean mSurfaceAvailable;

    public CameraSourcePreview(Context context) {
        super(context);
        initialize();
    }

    public CameraSourcePreview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        getHolder().addCallback(updateUI);
        mStartRequested = false;
        mSurfaceAvailable = false;
        createCameraSource();
    }

    public void start() throws IOException, SecurityException {
        if (mCameraSource != null) {
            mStartRequested = true;
            startIfReady();
        }
    }

    public void stop(){
        if(mCameraSource != null){
            mCameraSource.stop();
        }
    }

    public void release(){
        if(mCameraSource != null){
            mCameraSource.release();
        }
    }

    public void setListener(BarcodeTracker.Listener listener){
        BarcodeTracker tracker = new BarcodeTracker(listener);
        mBarcodeDetector.setProcessor(new MultiProcessor.Builder<>(tracker).build());
    }

    void createCameraSource(){
        Context context = getContext().getApplicationContext();
        mBarcodeDetector = new BarcodeDetector.Builder(context).build();

        CameraSource.Builder builder = new CameraSource.Builder(context, mBarcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder.setAutoFocusEnabled(true);
        }

        mCameraSource = builder.build();
    }

    void startIfReady() throws IOException, SecurityException {
        if (mStartRequested && mSurfaceAvailable) {
            mCameraSource.start(getHolder());
            mStartRequested = false;
        }
    }

    SurfaceHolder.Callback updateUI = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mSurfaceAvailable = true;
            try {
                startIfReady();
            } catch (SecurityException se) {
                Log.e(TAG, "Do not have permission to start the camera", se);
            } catch (IOException e) {
                Log.e(TAG, "Could not start camera source.", e);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mSurfaceAvailable = false;
        }
    };
}
