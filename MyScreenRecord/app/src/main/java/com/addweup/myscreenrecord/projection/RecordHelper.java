package com.addweup.myscreenrecord.projection;

import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;


/**
 * Created by denny on 2017/9/26.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RecordHelper extends MediaProjection.Callback{

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private static final String FILENAME = "GamePlayback.mp4";

    MediaProjection mediaProjection;
    VirtualDisplay virtualDisplay;
    MediaRecorder mediaRecorder;

    public void init(MediaProjection mediaProjection){
        try {
            getFilePath().delete();
            this.mediaProjection = mediaProjection;
            mediaProjection.registerCallback(this, null);
            initMediaRecorder();
            virtualDisplay = createVirtualDisplay(mediaProjection);
        } catch (IOException e) {
            e.printStackTrace();
            mediaRecorder = null;
            mediaProjection = null;
            virtualDisplay = null;
        }
    }

    private void initMediaRecorder() throws IOException {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(getFilePath().getAbsolutePath());
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setVideoSize(WIDTH, HEIGHT);
        mediaRecorder.setVideoEncodingBitRate(512 * 1000);
        mediaRecorder.setVideoFrameRate(30);
        mediaRecorder.prepare();
    }

    private VirtualDisplay createVirtualDisplay(MediaProjection mediaProjection) {
        return mediaProjection.createVirtualDisplay("GamePlayback",
                WIDTH, HEIGHT, 1,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mediaRecorder.getSurface(), null /*Callbacks*/, null /*Handler*/);
    }

    public void start(){
        mediaRecorder.start();
    }

    public void stop(){
        if(mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        mediaProjection = null;
        virtualDisplay = null;
    }

    public static File getFilePath(){
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FILENAME);
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }
}
