package com.addweup.mybarcode;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.microblink.activity.Pdf417ScanActivity;
import com.microblink.hardware.camera.CameraType;
import com.microblink.recognition.InvalidLicenceKeyException;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkbarcode.pdf417.Pdf417RecognizerSettings;
import com.microblink.recognizers.blinkbarcode.pdf417.Pdf417ScanResult;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;
import com.microblink.recognizers.settings.RecognizerSettingsUtils;
import com.microblink.results.barcode.BarcodeDetailedData;
import com.microblink.util.RecognizerCompatibility;
import com.microblink.util.RecognizerCompatibilityStatus;
import com.microblink.view.CameraAspectMode;
import com.microblink.view.CameraEventsListener;
import com.microblink.view.recognition.RecognizerView;
import com.microblink.view.recognition.ScanResultListener;

public class MicroBlinkActivity extends AppCompatActivity implements ScanResultListener, CameraEventsListener {

    private static final int PERMISSION_CAMERA_REQUEST_CODE = 69;
    private RecognizerView mRecognizerView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create RecognizerView
        mRecognizerView = new RecognizerView(this);

        RecognitionSettings settings = new RecognitionSettings();
        // setup array of recognition settings (described in chapter "Recognition
        // settings and results")
        RecognizerSettings[] settArray = setupSettingsArray();
        if(!RecognizerCompatibility.cameraHasAutofocus(CameraType.CAMERA_BACKFACE, this)) {
            settArray = RecognizerSettingsUtils.filterOutRecognizersThatRequireAutofocus(settArray);
        }
        settings.setRecognizerSettingsArray(settArray);
        mRecognizerView.setRecognitionSettings(settings);

        try {
            // set license key
            mRecognizerView.setLicenseKey("MXOGXYI7-OBDYTCF2-KHTWCFSV-ZSQ4QRWW-TLEENVU2-ZBDNNGWI-I3LJVSCG-22NJSAMB");
//            mRecognizerView.setLicenseKey(this, "your license key");
        } catch (InvalidLicenceKeyException exc) {
            finish();
            return;
        }

        // scan result listener will be notified when scan result gets available
        mRecognizerView.setScanResultListener(this);
        // camera events listener will be notified about camera lifecycle and errors
        mRecognizerView.setCameraEventsListener(this);

        // set camera aspect mode
        // ASPECT_FIT will fit the camera preview inside the view
        // ASPECT_FILL will zoom and crop the camera preview, but will use the
        // entire view surface
        mRecognizerView.setAspectMode(CameraAspectMode.ASPECT_FILL);

        mRecognizerView.create();

        View rootview = getLayoutInflater().inflate( R.layout.activity_micro_blink, null);
        textView = (TextView) rootview.findViewById(R.id.display);
        mRecognizerView.addView(rootview);

        setContentView(mRecognizerView);
    }

    private RecognizerSettings[] setupSettingsArray() {
        Pdf417RecognizerSettings sett = new Pdf417RecognizerSettings();
        // disable scanning of white barcodes on black background
        sett.setInverseScanning(false);
        // allow scanning of barcodes that have invalid checksum
        sett.setUncertainScanning(true);
        // disable scanning of barcodes that do not have quiet zone
        // as defined by the standard
        sett.setNullQuietZoneAllowed(false);

        // now add sett to recognizer settings array that is used to configure
        // recognition
        return new RecognizerSettings[] { sett };
    }

    @Override
    protected void onStart() {
        super.onStart();
        // you need to pass all activity's lifecycle methods to RecognizerView
        mRecognizerView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // you need to pass all activity's lifecycle methods to RecognizerView
        mRecognizerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // you need to pass all activity's lifecycle methods to RecognizerView
        mRecognizerView.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // you need to pass all activity's lifecycle methods to RecognizerView
        mRecognizerView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // you need to pass all activity's lifecycle methods to RecognizerView
        mRecognizerView.destroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // you need to pass all activity's lifecycle methods to RecognizerView
        mRecognizerView.changeConfiguration(newConfig);
    }

    int count = 0;
    @Override
    public void onScanningDone(RecognitionResults results) {
        // this method is from ScanResultListener and will be called when scanning completes
        // RecognitionResults may contain multiple results in array returned
        // by method getRecognitionResults().
        // This depends on settings in RecognitionSettings object that was
        // given to RecognizerView.
        // For more information, see chapter "Recognition settings and results")

        // After this method ends, scanning will be resumed and recognition
        // state will be retained. If you want to prevent that, then
        // you should call:
        // mRecognizerView.resetRecognitionState();

        // If you want to pause scanning to prevent receiving recognition
        // results, you should call:
        // mRecognizerView.pauseScanning();
        // After scanning is paused, you will have to resume it with:
        // mRecognizerView.resumeScanning(true);
        // boolean in resumeScanning method indicates whether recognition
        // state should be automatically reset when resuming scanning
        BaseRecognitionResult[] dataArray = results.getRecognitionResults();
        final StringBuilder stringBuilder = new StringBuilder();
        for(BaseRecognitionResult baseResult : dataArray) {
            if(baseResult instanceof Pdf417ScanResult) {
                Pdf417ScanResult result = (Pdf417ScanResult) baseResult;
                // getStringData getter will return the string version of barcode contents
                String barcodeData = result.getStringData();
                // isUncertain getter will tell you if scanned barcode is uncertain
                boolean uncertainData = result.isUncertain();
                stringBuilder.append(String.format("BarcodeData: %s, UncertainData: %b", barcodeData, uncertainData));
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!stringBuilder.toString().equals(textView.getText())){
                    textView.setText(stringBuilder.toString());
                }
            }
        });
    }

    @Override
    public void onCameraPreviewStarted() {
        // this method is from CameraEventsListener and will be called when camera preview starts
    }

    @Override
    public void onCameraPreviewStopped() {
        // this method is from CameraEventsListener and will be called when camera preview stops
    }

    @Override
    public void onError(Throwable exc) {
        /**
         * This method is from CameraEventsListener and will be called when
         * opening of camera resulted in exception or recognition process
         * encountered an error. The error details will be given in exc
         * parameter.
         */
    }

    @Override
    @TargetApi(23)
    public void onCameraPermissionDenied() {
        /**
         * Called on Android 6.0 and newer if camera permission is not given
         * by user. You should request permission from user to access camera.
         */
//        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
        /**
         * Please note that user might have not given permission to use
         * camera. In that case, you have to explain to user that without
         * camera permissions scanning will not work.
         * For more information about requesting permissions at runtime, check
         * this article:
         * https://developer.android.com/training/permissions/requesting.html
         */
    }

    @Override
    public void onAutofocusFailed() {
        /**
         * This method is from CameraEventsListener will be called when camera focusing has failed.
         * Camera manager usually tries different focusing strategies and this method is called when all
         * those strategies fail to indicate that either object on which camera is being focused is too
         * close or ambient light conditions are poor.
         */
    }

    @Override
    public void onAutofocusStarted(Rect[] areas) {
        /**
         * This method is from CameraEventsListener and will be called when camera focusing has started.
         * You can utilize this method to draw focusing animation on UI.
         * Areas parameter is array of rectangles where focus is being measured.
         * It can be null on devices that do not support fine-grained camera control.
         */
    }

    @Override
    public void onAutofocusStopped(Rect[] areas) {
        /**
         * This method is from CameraEventsListener and will be called when camera focusing has stopped.
         * You can utilize this method to remove focusing animation on UI.
         * Areas parameter is array of rectangles where focus is being measured.
         * It can be null on devices that do not support fine-grained camera control.
         */
    }
}
