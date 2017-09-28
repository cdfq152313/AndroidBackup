package com.addweup.myscreenrecord.projection;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.addweup.myscreenrecord.R;
import com.addweup.myscreenrecord.ui.DrawView;

import java.io.File;
import java.io.IOException;

public class MediaProjectionActivity extends AppCompatActivity {

    private static final String FILENAME = "MyScreenRecordTest.mp4";
    private static final String TAG = "MediaProjectAct";

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    public static final int REQUEST_CODE = 9547;

    DrawView drawView;
    MediaProjectionManager mediaProjectionManager;
    RecordHelper recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_porjection);

        mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    public void clearClick(View view){
        drawView.clear();
    }

    public void switchClick(View view){
        if(recorder == null){
            Intent captureIntent = mediaProjectionManager.createScreenCaptureIntent();
            startActivityForResult(captureIntent, REQUEST_CODE);
        }else{
            recorder.stop();
            recorder = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: ");
        if (requestCode != REQUEST_CODE) {
            Log.e(TAG, "Unknown request code: " + requestCode);
            return;
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(this,  "User denied screen sharing permission", Toast.LENGTH_SHORT).show();
            return;
        }

        recorder = new RecordHelper();
        recorder.init(mediaProjectionManager.getMediaProjection(resultCode, data));
        recorder.start();
    }
}
