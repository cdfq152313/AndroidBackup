package com.example.denny.mybarcode2.google.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.util.Log;

import com.example.denny.mybarcode2.GlobalApplication;

import java.util.List;

/**
 * Created by denny on 2017/7/20.
 */

public class CameraHelper {

    private static final String TAG = "CameraHelper";

    static CameraHelper instance;
    public static CameraHelper getInstance() {
        if(instance == null){
            instance = new CameraHelper();
        }
        return instance;
    }

    public Size getAvailableSize(){
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return getAvailableSizeOld();
            } else {
                return getAvailableSizeNew();
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    Size getAvailableSizeOld(){
        Camera camera = Camera.open(0);
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        for(Camera.Size size: sizes){
            camera.release();
            return new Size(size.width, size.height);
        }
        camera.release();
        return null;
    }

    @TargetApi(21)
    Size getAvailableSizeNew() throws CameraAccessException {
        Context context = GlobalApplication.getInstance().getApplicationContext();
        CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        String cameraId = manager.getCameraIdList()[0];
        CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap configs = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        android.util.Size[] sizes = configs.getOutputSizes(SurfaceTexture.class);
        for(android.util.Size size: sizes){
            Log.i(TAG, String.format("getAvailableSizeNew: Width: %d, Height: %d", size.getWidth(), size.getHeight()));
            return new Size(size.getWidth(), size.getHeight());
        }
        return null;
    }

    public String getInfo(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return getInfoOld();
        } else {
            return getInfoNew();
        }
    }

    public void displayAvailableSize(){
        Camera camera = Camera.open(0);
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        for(Camera.Size size: sizes){
            Log.i(TAG, String.format("Size: %d %d", size.width, size.height));
        }
        camera.release();
    }

    String getInfoOld(){
        int number = Camera.getNumberOfCameras();
        return String.format("CameraNumber: %s", number);
    }

    String getInfoNew(){
        try {
            Context context = GlobalApplication.getInstance().getApplicationContext();
            CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            String [] list = new String[0];
            list = manager.getCameraIdList();
            StringBuilder builder = new StringBuilder();
            for(String camera : list){
                builder.append(camera);
                builder.append("\n");
            }
            return String.format("CameraNumber: %s\n%s", list.length, builder.toString());
        } catch (CameraAccessException e) {
            return "CameraAccessException";
        }
    }

    public static class Size{
        public int width;
        public int height;
        public Size(int width, int height){
            this.width = width;
            this.height = height;
        }
    }
}
