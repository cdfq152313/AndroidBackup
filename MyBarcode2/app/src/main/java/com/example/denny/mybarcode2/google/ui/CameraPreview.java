package com.example.denny.mybarcode2.google.ui;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;

/**
 * Created by denny on 2017/7/20.
 */

public class CameraPreview extends SurfaceView {
    public static final String TAG = "CameraPreview";
    int width;
    int height;

    public CameraPreview(Context context, int width, int height) {
        super(context);
        // 假設皆為縱向
        this.width = height;
        this.height = width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        printSpec(widthMeasureSpec, true);
        printSpec(heightMeasureSpec, false);

        // 保持比例填滿整個空間

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        double wScale = (double) width / (double) this.width;
        double hScale = (double) height / (double) this.height;
        if(wScale > hScale){
            height = (int) Math.ceil(this.height * wScale);
        }else{
            width = (int) Math.ceil(this.width * hScale);
        }

        setMeasuredDimension(width, height);
        Log.i(TAG, String.format("onMeasure: Width %d, Height %d", width,height));
    }

    void printSpec(int spec, boolean width){
        String display;
        if(width){
            display = "widthMeasureSpec: %s, %d";
        }else{
            display = "heightMeasureSpec: %s, %d";
        }
        int mode = MeasureSpec.getMode(spec);
        int size = MeasureSpec.getSize(spec);
        switch (mode){
            case MeasureSpec.AT_MOST:
                Log.i(TAG, String.format(display, "AT MOST", size));
                break;
            case MeasureSpec.EXACTLY:
                Log.i(TAG, String.format(display, "EXACTLY", size));
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, String.format(display, "UNSPECIFIED", size));
                break;
        }
    }
}
