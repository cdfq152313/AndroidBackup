package com.addweup.myscreenrecord.camera;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;

import com.addweup.myscreenrecord.R;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements Camera.PreviewCallback {

    private static final String FILENAME = "MyScreenRecordTest.mp4";

    private static final String TAG = "camera";
    Camera camera;
    SurfaceView surfaceView;
    TextureView textureView;
    MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        init();
    }

    void init(){
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        textureView = (TextureView) findViewById(R.id.textureView);
    }

    public void switchClick(View view){
        if(camera == null){
            startPreview();
        }else{
            stopPreview();
        }
    }

    private void startPreview(){
        try {
            camera = Camera.open(1);
            camera.setPreviewCallback(this);
            camera.setPreviewDisplay(surfaceView.getHolder());
//            initMediaRecorder(camera);
//            mediaRecorder.start();
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMediaRecorder(Camera camera){
        try {
            camera.unlock();
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setCamera(camera);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILENAME);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setOutputFile(path.getAbsolutePath());
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPreview(){
//        mediaRecorder.stop();
//        mediaRecorder.release();
//        mediaRecorder = null;

        camera.stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Log.i(TAG, "onPreviewFrame: ");
    }
}
