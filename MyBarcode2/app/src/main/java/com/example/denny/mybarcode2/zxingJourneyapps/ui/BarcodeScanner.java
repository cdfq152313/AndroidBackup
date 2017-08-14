package com.example.denny.mybarcode2.zxingJourneyapps.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.example.denny.mybarcode2.google.ui.BarcodeTracker;
import com.example.denny.mybarcode2.google.ui.CameraHelper;
import com.example.denny.mybarcode2.google.ui.CameraPreview;

/**
 * Created by denny on 2017/5/4.
 */

public class BarcodeScanner extends FrameLayout {
    static final String TAG = "BarcodeScanner";

    SurfaceView mSurfaceView;

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
        mSurfaceView = createSurfaceView();
    }

    public void setListener(BarcodeTracker.Listener listener){

    }

    SurfaceView createSurfaceView(){
        SurfaceView surfaceView = new CameraPreview(getContext(), size.width, size.height);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });

        addView(surfaceView);
        return surfaceView;
    }
}
