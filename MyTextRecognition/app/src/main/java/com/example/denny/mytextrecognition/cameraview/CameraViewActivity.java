package com.example.denny.mytextrecognition.cameraview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.denny.mytextrecognition.R;
import com.google.android.cameraview.CameraView;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CameraViewActivity extends AppCompatActivity {
    public static final String TAG = "CameraView";

    CameraView cameraView;
    TextView display;
    TessBaseAPI baseApi;

    Executor executor;
    Handler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        initCamera();
        initTessBaseAPI();
        display = (TextView) findViewById(R.id.display);
        executor = Executors.newSingleThreadExecutor();
        uiHandler = new Handler();
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    public void takePictureClick(View view){
        cameraView.takePicture();
    }

    CameraView.Callback parseImage = new CameraView.Callback() {
        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    Bitmap bImage = loadImage(data);
                    final String result = parseData(bImage);
                    long end = System.currentTimeMillis();
                    Log.i(TAG, "Process Tsime : " + (end-start) + "ms" );
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            display.setText(result);
                        }
                    });
                }
            });
        }
    };

    public Bitmap loadImage(byte [] data){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bImage = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        return bImage;
    }

    public String parseData(Bitmap bImage){
        File download = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        baseApi.init(download.getAbsolutePath(), "eng");
        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
        baseApi.setImage(bImage);

        String result = baseApi.getUTF8Text();
        baseApi.clear();
        baseApi.end();
        return result;
    }
}
