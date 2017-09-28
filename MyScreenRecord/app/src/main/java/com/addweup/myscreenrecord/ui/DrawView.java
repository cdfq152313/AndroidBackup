package com.addweup.myscreenrecord.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.OutputStream;

/**
 * Created by denny on 2017/8/23.
 */

public class DrawView extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = "DrawView";

    Bitmap bitmap;
    Canvas canvas;
    boolean start = false;
    Paint paint;
    int backGroungColor = Color.parseColor("#f7f7f7");

    float startX;
    float startY;

    public DrawView(Context context, AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!start){
            return true;
        }

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                canvas.drawLine(startX, startY, event.getX(), event.getY(), paint);
                updateSurfaceView();
                startX = event.getX();
                startY = event.getY();
                break;
        }
        return true;
    }

    void updateSurfaceView(){
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawBitmap(bitmap, 0, 0, null);
        getHolder().unlockCanvasAndPost(canvas);
    }

    public void clear(){
        // clear bitmap
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);
        this.canvas.drawColor(backGroungColor);

        // clear canvas
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(backGroungColor);
        getHolder().unlockCanvasAndPost(canvas);
    }


    public void save(OutputStream stream){
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated: ");
        start = true;
        clear();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
