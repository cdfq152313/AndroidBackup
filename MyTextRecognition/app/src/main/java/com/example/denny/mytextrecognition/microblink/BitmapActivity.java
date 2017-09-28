package com.example.denny.mytextrecognition.microblink;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denny.mytextrecognition.R;
import com.microblink.directApi.DirectApiErrorListener;
import com.microblink.directApi.Recognizer;
import com.microblink.hardware.orientation.Orientation;
import com.microblink.recognition.FeatureNotSupportedException;
import com.microblink.recognition.InvalidLicenceKeyException;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognitionResult;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognizerSettings;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;
import com.microblink.results.ocr.OcrResult;
import com.microblink.view.recognition.ScanResultListener;

import java.io.IOException;
import java.util.List;

public class BitmapActivity extends AppCompatActivity implements ScanResultListener, TextureView.SurfaceTextureListener {

    private static final String TAG = "Bitmap";

    Recognizer mRecognizer;
    Camera camera;
    TextureView textureView;
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        camera = Camera.open(0);
        textureView = (TextureView) findViewById(R.id.textureView);
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.autoFocus(null);
            }
        });
        textureView.setSurfaceTextureListener(this);
        display = (TextView) findViewById(R.id.display);
        initMicroBlink();
    }

    private void initMicroBlink(){
        try {
            mRecognizer = Recognizer.getSingletonInstance();
        } catch (FeatureNotSupportedException e) {
            Toast.makeText(this, "Feature not supported! Reason: " + e.getReason().getDescription(), Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        try {
            // set license key
            mRecognizer.setLicenseKey(this, "RGDQ4HOZ-SOWAK7K3-KWQFMSSK-62TQCNGN-FWDPQ37Z-HTFY6XKF-XRXCCJBQ-R3W5UNGO");
        } catch (InvalidLicenceKeyException exc) {
            finish();
            return;
        }
        RecognitionSettings settings = new RecognitionSettings();
        // setupSettingsArray method is described in chapter "Recognition
        // settings and results")
        settings.setRecognizerSettingsArray(setupSettingsArray());
        mRecognizer.initialize(this, settings, new DirectApiErrorListener() {
            @Override
            public void onRecognizerError(Throwable t) {
                Toast.makeText(BitmapActivity.this, "There was an error in initialization of Recognizer: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private RecognizerSettings[] setupSettingsArray() {
        MRTDRecognizerSettings sett = new MRTDRecognizerSettings();

        // now add sett to recognizer settings array that is used to configure
        // recognition
        return new RecognizerSettings[] { sett };
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeCamera();
    }

    private void closeCamera(){
        camera.stopPreview();
        camera.release();
    }

    public void scanClick(View view){
        Bitmap bitmap = textureView.getBitmap();
        mRecognizer.recognizeBitmap(bitmap, Orientation.ORIENTATION_PORTRAIT, this);
    }


    Handler handler = new Handler();
    @Override
    public void onScanningDone(@Nullable final RecognitionResults recognitionResults) {
        BaseRecognitionResult[] dataArray = recognitionResults.getRecognitionResults();
        if(dataArray == null){
            displayResult("Null");
            return;
        }
        for(BaseRecognitionResult baseResult : dataArray) {
            if(baseResult instanceof MRTDRecognitionResult) {
                MRTDRecognitionResult result = (MRTDRecognitionResult) baseResult;
                // you can use getters of MRTDRecognitionResult class to
                // obtain scanned information
                if(result.isValid() && !result.isEmpty()) {
                    if(result.isMRZParsed()) {
                        PassportData data = new PassportData();
                        data.name = String.format("%s %s" ,result.getPrimaryId(),result.getSecondaryId());
                        data.nationality = result.getNationality();
                        data.passport_number = result.getDocumentNumber();
                        data.expire_date = result.getRawDateOfExpiry();
                        data.date_of_birth = result.getRawDateOfBirth();
                        data.gender = result.getSex();
                        data.raw_data = result.getMRZText();
                        displayResult(data.toString());
                    } else {
                        OcrResult rawOcr = result.getOcrResult();
                        // attempt to parse OCR result by yourself
                        // or ask user to try again
                    }

                } else {
                    // not all relevant data was scanned, ask user
                    // to try again
                }
            }
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            camera.setDisplayOrientation(90);
            camera.setPreviewTexture(textureView.getSurfaceTexture());
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void displayResult(final String result){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, result);
                display.setText(result);
            }
        });
    }
}
