package com.addweup.mymicrophone;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.IOException;

/**
 * Created by cdfq1 on 2017/2/6.
 */

public class MyMediaRecord {
    MediaRecorder mediaRecorder = null;
    boolean isRecordCompleted = false;
    String path;

    private MediaRecorder initMediaRecorder(String path) throws InitializeError {
        try{
            this.path = path;
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setOutputFile(path);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mediaRecorder.prepare();
            return mediaRecorder;
        }
        catch (IOException e){
            throw new InitializeError();
        }
    }

    public void start(String path) throws InitializeError, AlreadyStart {
        if(mediaRecorder == null){
            mediaRecorder = initMediaRecorder(path);
            mediaRecorder.start();
        }
        else{
            throw new AlreadyStart();
        }
    }

    public void stop() throws AlreadyStop {
        if(mediaRecorder != null){
            isRecordCompleted = true;
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        else{
            throw new AlreadyStop();
        }
    }

    public void play() throws NoRecordFile {
        if(mediaRecorder == null && isRecordCompleted){
            try {
                MediaPlayer mPlayer = new MediaPlayer();
                mPlayer.setDataSource(path);
                mPlayer.prepare();
                mPlayer.start();
            }
            catch (IOException e){
                throw new NoRecordFile();
            }
        }
        else{
            throw new NoRecordFile();
        }
    }

    class InitializeError extends Exception{

    }
    class AlreadyStart extends Exception{

    }
    class AlreadyStop extends Exception{

    }
    class NoRecordFile extends Exception{

    }
}
