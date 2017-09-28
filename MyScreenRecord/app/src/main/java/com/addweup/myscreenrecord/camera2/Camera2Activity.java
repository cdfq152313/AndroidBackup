package com.addweup.myscreenrecord.camera2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaRecorder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.addweup.myscreenrecord.R;

import java.util.concurrent.TimeUnit;

public class Camera2Activity extends AppCompatActivity{

    private static final String TAG = "Camera2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
    }

    public void switchClick(View view) {

    }

//    MediaRecorder mediaRecorder;
//
//    private void openCamera(int width, int height) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        try {
//            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//            String cameraId = manager.getCameraIdList()[1];
//            // Choose the sizes for camera preview and video recording
//            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
//            mediaRecorder = new MediaRecorder();
//            manager.openCamera(cameraId, this, null);
//        } catch (CameraAccessException e) {
//            Toast.makeText(this, "Cannot access the camera.", Toast.LENGTH_SHORT).show();
//        } catch (NullPointerException e) {
//            // Currently an NPE is thrown when the Camera2API is used but not supported on the
//            // device this code runs.
//            Toast.makeText(this, "Null PointerException", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void closeCamera() {
//        try {
//            mCameraOpenCloseLock.acquire();
//            closePreviewSession();
//            if (null != mCameraDevice) {
//                mCameraDevice.close();
//                mCameraDevice = null;
//            }
//            if (null != mMediaRecorder) {
//                mMediaRecorder.release();
//                mMediaRecorder = null;
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException("Interrupted while trying to lock camera closing.");
//        } finally {
//            mCameraOpenCloseLock.release();
//        }
//    }
//
//    /**
//     * Start the camera preview.
//     */
//    private void startPreview() {
//        if (null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
//            return;
//        }
//        try {
//            closePreviewSession();
//            SurfaceTexture texture = mTextureView.getSurfaceTexture();
//            assert texture != null;
//            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
//            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//
//            Surface previewSurface = new Surface(texture);
//            mPreviewBuilder.addTarget(previewSurface);
//
//            mCameraDevice.createCaptureSession(Collections.singletonList(previewSurface),
//                    new CameraCaptureSession.StateCallback() {
//
//                        @Override
//                        public void onConfigured(@NonNull CameraCaptureSession session) {
//                            mPreviewSession = session;
//                            updatePreview();
//                        }
//
//                        @Override
//                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
//                            Activity activity = getActivity();
//                            if (null != activity) {
//                                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }, mBackgroundHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Update the camera preview. {@link #startPreview()} needs to be called in advance.
//     */
//    private void updatePreview() {
//        if (null == mCameraDevice) {
//            return;
//        }
//        try {
//            setUpCaptureRequestBuilder(mPreviewBuilder);
//            HandlerThread thread = new HandlerThread("CameraPreview");
//            thread.start();
//            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, mBackgroundHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onOpened(@NonNull CameraDevice cameraDevice) {
//
//    }
//
//    @Override
//    public void onDisconnected(@NonNull CameraDevice cameraDevice) {
//
//    }
//
//    @Override
//    public void onError(@NonNull CameraDevice cameraDevice, @IntDef(value = {CameraDevice.StateCallback.ERROR_CAMERA_IN_USE, CameraDevice.StateCallback.ERROR_MAX_CAMERAS_IN_USE, CameraDevice.StateCallback.ERROR_CAMERA_DISABLED, CameraDevice.StateCallback.ERROR_CAMERA_DEVICE, CameraDevice.StateCallback.ERROR_CAMERA_SERVICE}) int i) {
//
//    }
}
