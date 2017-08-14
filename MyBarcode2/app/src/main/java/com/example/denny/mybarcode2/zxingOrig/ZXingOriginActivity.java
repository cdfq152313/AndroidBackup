package com.example.denny.mybarcode2.zxingOrig;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.denny.mybarcode2.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ZXingOriginActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback{

    private static final String TAG = "ZXingOriginActivity";

    Handler mHandler = new Handler();
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_origin);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
//        timerAutoFocus();
    }

    void timerAutoFocus(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFocusMode("auto");
                mCamera.setParameters(parameters);
                timerAutoFocus();
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewCallback(this);
        } catch (IOException e) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFocusMode("auto");
        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;
    }

    int count = 0;
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        count += 1;
        if(count > 5){
            count = 0;
            Bitmap bitmap = toBitmap(data, camera);
            String result = toResult(bitmap);
            Log.w(TAG, "onPreviewFrame: " + result);
        }
    }

    Bitmap toBitmap(byte[] data, Camera camera){
        Camera.Parameters parameters = camera.getParameters();
        int width = parameters.getPreviewSize().width;
        int height = parameters.getPreviewSize().height;

        YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), width, height, null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);

        byte[] bytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    String toResult(Bitmap bitmap){
            //QRCode影像解碼
            Reader [] readers = {new PDF417Reader(), new QRCodeReader()};
            BinaryBitmap binaryBitmap = toBinaryBitmap(bitmap);
            //取得解碼資訊
            for(Reader reader: readers) {
                try {
                    Result result = reader.decode(binaryBitmap);
                    if(result.getText() != null){
                        return result.getText();
                    }
                } catch (ChecksumException | FormatException | NotFoundException e) {
                    e.printStackTrace();
                }
            }
            return null;
    }

    BinaryBitmap toBinaryBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        return binaryBitmap;
    }

//    private void decode(byte[] data, int width, int height) {
//        long start = System.currentTimeMillis();
//        Result rawResult = null;
//        PlanarYUVLuminanceSource source = buildLuminanceSource(data, width, height);
//        if (source != null) {
//            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//            try {
//                rawResult = multiFormatReader.decodeWithState(bitmap);
//            } catch (ReaderException re) {
//                // continue
//            } finally {
//                multiFormatReader.reset();
//            }
//        }
//    }
//
//    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
//        Rect rect = getFramingRectInPreview();
//        if (rect == null) {
//            return null;
//        }
//        // Go ahead and assume it's YUV rather than die.
//        return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top,
//                rect.width(), rect.height(), false);
//    }
//
//    public synchronized Rect getFramingRectInPreview() {
//        if (framingRectInPreview == null) {
//            Rect framingRect = getFramingRect();
//            if (framingRect == null) {
//                return null;
//            }
//            Rect rect = new Rect(framingRect);
//            Point cameraResolution = configManager.getCameraResolution();
//            Point screenResolution = configManager.getScreenResolution();
//            if (cameraResolution == null || screenResolution == null) {
//                // Called early, before init even finished
//                return null;
//            }
//            rect.left = rect.left * cameraResolution.x / screenResolution.x;
//            rect.right = rect.right * cameraResolution.x / screenResolution.x;
//            rect.top = rect.top * cameraResolution.y / screenResolution.y;
//            rect.bottom = rect.bottom * cameraResolution.y / screenResolution.y;
//            framingRectInPreview = rect;
//        }
//        return framingRectInPreview;
//    }
}
