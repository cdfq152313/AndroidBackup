package com.addweup.mymicrophone;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yalantis.waves.util.Horizon;

import java.io.File;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    final String recordFileName = "ReportVoice";
    String recordFilePath;

    Horizon mHorizon;
    GLSurfaceView glSurfaceView;
    Handler handler = new Handler();
    byte[] buffer = new byte[128];
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glSurfaceView = (GLSurfaceView) findViewById(R.id.glSurfaceView);
        mHorizon = new Horizon(glSurfaceView, getResources().getColor(R.color.background),
                44100, 2, 16);

        //here we put some sound data to the buffer
        handler.post(new Runnable() {
            @Override
            public void run() {
                random.nextBytes(buffer);
                mHorizon.updateView(buffer);
                handler.postDelayed(this, 500);
            }
        });

        File file = new File(getCacheDir(), recordFileName);
        recordFilePath = file.getAbsolutePath();
        myMediaRecord = new MyMediaRecord();
    }

    MyMediaRecord myMediaRecord;

    public void startClick(View view){
        try {
            myMediaRecord.start(recordFilePath);
            Toast.makeText(this, "Start Record", Toast.LENGTH_SHORT).show();
        } catch (MyMediaRecord.InitializeError e) {
            Toast.makeText(this, "Initialize Error", Toast.LENGTH_SHORT).show();
        } catch (MyMediaRecord.AlreadyStart e){
            Toast.makeText(this, "Already Start", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopClick(View view){
        try {
            myMediaRecord.stop();
            Toast.makeText(this, "Stop Record", Toast.LENGTH_SHORT).show();
        }
        catch (MyMediaRecord.AlreadyStop e){
            Toast.makeText(this, "Already Stop", Toast.LENGTH_SHORT).show();
        }
    }

    public void playClick(View view){
        try {
            myMediaRecord.play();
            Toast.makeText(this, "Start Play", Toast.LENGTH_SHORT).show();
        } catch (MyMediaRecord.NoRecordFile e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        }
    }
}
