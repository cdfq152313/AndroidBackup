package com.example.denny.mytextrecognition.cameraview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.denny.mytextrecognition.R;
import com.google.android.cameraview.CameraView;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

public class CameraViewActivity extends AppCompatActivity {
    public static final String TAG = "CameraView";

    CameraView cameraView;
    Handler handler;
    HandlerThread handlerThread;
    TessBaseAPI baseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        initCamera();
        initHandler();
        initTessBaseAPI();
    }

    public void initHandler(){
        handlerThread = new HandlerThread("Worker");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    public void initCamera(){
        cameraView = (CameraView) findViewById(R.id.camera_preview);
        cameraView.addCallback(parseImage);
    }

    public void initTessBaseAPI(){
        baseApi = new TessBaseAPI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
        handler.post(takePictureContinuously);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(takePictureContinuously);
        cameraView.stop();
    }

    Runnable takePictureContinuously = new Runnable() {
        @Override
        public void run() {
            cameraView.takePicture();
            handler.postDelayed(takePictureContinuously, 1500);
        }
    };


    CameraView.Callback parseImage = new CameraView.Callback() {
        @Override
        public void onPictureTaken(CameraView cameraView, byte[] data) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bImage = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File [] files = download.listFiles();
            for(File file: files){
                if(file.getName().equals("tesseract")){
                    Log.i(TAG, "Parse start");
                    baseApi.init(file.getAbsolutePath(), null);
                    baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_BLOCK_VERT_TEXT);
                    baseApi.setImage(bImage);

                    Log.i(TAG, baseApi.getUTF8Text());
                    baseApi.clear();
                    baseApi.end();
                }
            }
        }
    };
}
