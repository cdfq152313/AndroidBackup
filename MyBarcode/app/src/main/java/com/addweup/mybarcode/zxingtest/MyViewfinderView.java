package com.addweup.mybarcode.zxingtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdfq1 on 2017/2/6.
 */

public class MyViewfinderView extends ViewfinderView {
    public MyViewfinderView(Context context, AttributeSet attrs ) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
//        refreshSizes();
//        if (framingRect == null || previewFramingRect == null) {
//            return;
//        }
//
//        Rect frame = framingRect;
//        Rect previewFrame = previewFramingRect;
//
//        int width = canvas.getWidth();
//        int height = canvas.getHeight();
//
//        // Draw the exterior (i.e. outside the framing rect) darkened
//        paint.setColor(resultBitmap != null ? resultColor : maskColor);
//        canvas.drawRect(0, 0, width, frame.top, paint);
//        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
//        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
//        canvas.drawRect(0, frame.bottom + 1, width, height, paint);
    }
}
