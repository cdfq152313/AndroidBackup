package com.example.denny.mycamera.camera2;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.denny.mycamera.R;

public class Camera2Activity extends AppCompatActivity {

    Handler handler;

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    ImageReader imageReader;

    CameraManager manager;
    CameraDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera2_act);

    }

//    void initSurfaceView(){
//        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
//        surfaceHolder = surfaceView.getHolder();
//        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//
//            }
//        });
//    }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    void initCameraAndPreview(){
//        manager = (CameraManager) getSystemService(CAMERA_SERVICE);
//
//        Log.d("linc","init camera and preview");
//        HandlerThread handlerThread = new HandlerThread("Camera2");
//        handlerThread.start();
//        handler = new Handler(handlerThread.getLooper());
//        try {
//            String cameraId = ""+CameraCharacteristics.LENS_FACING_FRONT;
//            imageReader = ImageReader.newInstance(surfaceView.getWidth(), surfaceView.getHeight(),
//                    ImageFormat.JPEG,/*maxImages*/7);
//            imageReader.setOnImageAvailableListener(mOnImageAvailableListener, mHandler);
//
//            manager.openCamera(cameraId, DeviceStateCallback, handler);
//        } catch (CameraAccessException e) {
//            Log.e("linc", "open camera failed." + e.getMessage());
//        } catch (SecurityException e){
//            e.printStackTrace();
//        }
//    }
//
//    CameraDevice.StateCallback DeviceStateCallback = new CameraDevice.StateCallback() {
//
//        @Override
//        public void onOpened(CameraDevice camera) {
//            Log.d("linc","DeviceStateCallback:camera was opend.");
//            mCameraOpenCloseLock.release();
//            mCameraDevice = camera;
//            try {
//                createCameraCaptureSession();
//            } catch (CameraAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    private void createCameraCaptureSession() throws CameraAccessException {
//        Log.d("linc","createCameraCaptureSession");
//
//        mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//        mPreviewBuilder.addTarget(mSurfaceHolder.getSurface());
//        mState = STATE_PREVIEW;
//        mCameraDevice.createCaptureSession(
//                Arrays.asList(mSurfaceHolder.getSurface(), mImageReader.getSurface()),
//                mSessionPreviewStateCallback, mHandler);
//    }
//
//    private CameraCaptureSession.StateCallback mSessionPreviewStateCallback = new
//            CameraCaptureSession.StateCallback() {
//
//                @Override
//                public void onConfigured(CameraCaptureSession session) {
//                    Log.d("linc","mSessionPreviewStateCallback onConfigured");
//                    mSession = session;
//                    try {
//                        mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE,
//                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
//                        mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE,
//                                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
//                        session.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);
//                    } catch (CameraAccessException e) {
//                        e.printStackTrace();
//                        Log.e("linc","set preview builder failed."+e.getMessage());
//                    }
//                }
//            };
//
//    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback =
//            new CameraCaptureSession.CaptureCallback() {
//
//                @Override
//                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request,
//                                               TotalCaptureResult result) {
//                    //            Log.d("linc","mSessionCaptureCallback, onCaptureCompleted");
//                    mSession = session;
//                    checkState(result);
//                }
//
//                @Override
//                public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request,
//                                                CaptureResult partialResult) {
//                    Log.d("linc","mSessionCaptureCallback,  onCaptureProgressed");
//                    mSession = session;
//                    checkState(partialResult);
//                }
//
//                private void checkState(CaptureResult result) {
//                    switch (mState) {
//                        case STATE_PREVIEW:
//                            // NOTHING
//                            break;
//                        case STATE_WAITING_CAPTURE:
//                            int afState = result.get(CaptureResult.CONTROL_AF_STATE);
//
//                            if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState ||
//                                    CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState
//                                    ||  CaptureResult.CONTROL_AF_STATE_PASSIVE_FOCUSED == afState
//                                    || CaptureResult.CONTROL_AF_STATE_PASSIVE_UNFOCUSED == afState) {
//                                //do something like save picture
//                            }
//                            break;
//                    }
//                }
//
//            };
//    public void onCapture(View view) {
//        try {
//            Log.i("linc", "take picture");
//            mState = STATE_WAITING_CAPTURE;
//            mSession.setRepeatingRequest(mPreviewBuilder.build(), mSessionCaptureCallback, mHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
}
