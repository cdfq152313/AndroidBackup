package com.example.denny.mybarcode2.own;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.denny.mybarcode2.R;
import com.example.denny.mybarcode2.google.ui.CameraHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OriginCameraActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback{

    public static final String TAG = "CameraActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin_camera);
        holder = new Holder();
        holder.surfaceHolder.addCallback(this);
    }

    Camera mCamera;
    Holder holder;
    boolean previewing = false;
    byte [] currentPreview;
    Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void cropClick(View view){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                crop(currentPreview);
            }
        });
    }

    Handler handler = new Handler();
    public void displayImage(final Bitmap bitmap){
        handler.post(new Runnable() {
            @Override
            public void run() {
                holder.imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(previewing){
            mCamera.stopPreview();
            previewing = false;
        }

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            previewing = true;
            mCamera.setPreviewCallback(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        previewing = false;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        currentPreview = data;
    }

    void crop(byte[] data){
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        Rectangle rectangle = new Rectangle(size.width, size.height, 0, 0, 800, 600);

//        data = cropRectangle(data, rectangle);
//        data = cropRectangle2(data, rectangle);
        data = cropRectangle3(data, rectangle);
        Bitmap bitmap = Bitmap.createBitmap(rectangle.width, rectangle.height, Bitmap.Config.ARGB_8888);
        Allocation bmData = renderScriptNV21ToRGBA888(
                this,
                rectangle.width,
                rectangle.height,
                data);
        bmData.copyTo(bitmap);
        displayImage(bitmap);
    }

    byte [] cropRectangle(byte [] data, Rectangle r){
        int newDataLength = r.width * r.height / 2 * 3;
        Log.i(TAG, String.format("Origin => length: %d, width: %d, height: %d", data.length, r.oWidth, r.oHeight));
        Log.i(TAG, String.format("Crop => length: %d, x: %d, y %d, width %d, height %d", newDataLength, r.x, r.y, r.width, r.height));

        byte [] newData = new byte[newDataLength];
        int uOStart = r.oWidth * r.oHeight;
        int vOStart = r.oWidth * r.oHeight / 4 * 5;

        int uStart = r.width * r.height;
        int vStart = r.width * r.height / 4 * 5;

        for(int i = 0; i < r.height; ++i){
            System.arraycopy(data, (i+r.y)*r.oWidth + r.x, newData, i*r.width, r.width);
            System.arraycopy(data, uOStart + (i+r.y)*(r.oWidth/4) + r.x/4, newData, uStart + i*(r.width/4), r.width/4);
            System.arraycopy(data, vOStart + (i+r.y)*(r.oWidth/4) + r.x/4, newData, vStart + i*(r.width/4), r.width/4);
        }
        return newData;
    }

    byte [] cropRectangle2(byte[] data, Rectangle r){
        byte [] newdata = new byte[data.length];
        System.arraycopy(data, 0, newdata, 0, data.length);
        return newdata;
    }

    byte [] cropRectangle3(byte[] data, Rectangle r){
        int newDataLength = r.width * r.height / 2 * 3;
        Log.i(TAG, String.format("Origin => length: %d, width: %d, height: %d", data.length, r.oWidth, r.oHeight));
        Log.i(TAG, String.format("Crop => length: %d, x: %d, y %d, width %d, height %d", newDataLength, r.x, r.y, r.width, r.height));

        byte [] newData = new byte[newDataLength];
        int uvOStart = r.oWidth * r.oHeight;
        int uvStart = r.width * r.height;

        boolean even = true;
        for(int i = 0; i < r.height; ++i){
            System.arraycopy(data, (i+r.y)*r.oWidth + r.x, newData, i*r.width, r.width);
            if(even){
                System.arraycopy(data, uvOStart + (i/2+r.y)*r.oWidth + r.x, newData, uvStart + i/2*r.width, r.width);
            }
            even = !even;
        }
        return newData;
    }

    public Allocation renderScriptNV21ToRGBA888(Context context, int width, int height, byte[] nv21) {
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs));

        Type.Builder yuvType = new Type.Builder(rs, Element.U8(rs)).setX(nv21.length);
        Allocation in = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT);

        Type.Builder rgbaType = new Type.Builder(rs, Element.RGBA_8888(rs)).setX(width).setY(height);
        Allocation out = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT);

        in.copyFrom(nv21);

        yuvToRgbIntrinsic.setInput(in);
        yuvToRgbIntrinsic.forEach(out);
        return out;
    }

    class Holder{
        Holder(){
            surface = (SurfaceView) findViewById(R.id.surfaceView);
            surfaceHolder = surface.getHolder();
            imageView = (ImageView) findViewById(R.id.imageView);
        }
        SurfaceView surface;
        SurfaceHolder surfaceHolder;
        ImageView imageView;
    }

    class Rectangle{
        Rectangle(int oWidth, int oHeight, int x, int y, int width, int height){
            this.oWidth = oWidth;
            this.oHeight = oHeight;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        int oWidth;
        int oHeight;
        int x;
        int y;
        int width;
        int height;
    }
}
