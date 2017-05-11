package com.example.denny.mytextrecognition.microblink;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;

import com.microblink.hardware.camera.CameraType;
import com.microblink.recognition.InvalidLicenceKeyException;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognizerSettings;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;
import com.microblink.recognizers.settings.RecognizerSettingsUtils;
import com.microblink.util.RecognizerCompatibility;
import com.microblink.view.CameraAspectMode;
import com.microblink.view.CameraEventsListener;
import com.microblink.view.recognition.RecognizerView;
import com.microblink.view.recognition.ScanResultListener;

public class CustomizeAcitivity extends Activity implements ScanResultListener, CameraEventsListener {

    public static final int RESULT_OK = 2882;
    public static final String EXTRAS_RECOGNITION_RESULTS = "RESULT";

    private static final int PERMISSION_CAMERA_REQUEST_CODE = 69;
    private RecognizerView mRecognizerView;

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
            mRecognizerView.setLicenseKey("INRI37N4-M4YTK7UV-73PPICOR-FOXVCMXL-I3DPHREE-27XIBUSB-KW6S6PVU-MFOTFEC3");
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

        setContentView(mRecognizerView);
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


    @Override
    public void onScanningDone(RecognitionResults results) {
        Intent intent = new Intent();
        intent.putExtra(EXTRAS_RECOGNITION_RESULTS, results);
        setResult(RESULT_OK, intent);
        finish();
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
        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
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

    private RecognizerSettings[] setupSettingsArray() {
        MRTDRecognizerSettings sett = new MRTDRecognizerSettings();

        // now add sett to recognizer settings array that is used to configure
        // recognition
        return new RecognizerSettings[] { sett };
    }
}
