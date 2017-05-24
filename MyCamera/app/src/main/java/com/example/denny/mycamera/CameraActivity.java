package com.example.denny.mycamera;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.denny.mycamera.camera.CameraFragment;
import com.example.denny.mycamera.cameraview.CameraViewFragment;
import com.example.denny.mycamera.display.DisplayFragment;

public class CameraActivity extends AppCompatActivity {

    public static final String INIT_FRAGMENT = "INIT_FRAGMENT";
    public static final int CAMERA_FRAGMENT = 1;
    public static final int CAMERA2_FRAGMENT = 2;
    public static final int CAMERAVIEW_FRAGMENT = 3;

    byte [] imageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        showInitFragment();
    }

    public void showInitFragment(){
        int frag = getIntent().getIntExtra(INIT_FRAGMENT, 3);
        switch (frag){
            case CAMERA_FRAGMENT:
                showCameraFragment();
                break;
            case CAMERA2_FRAGMENT:
                showCamera2Fragment();
                break;
            case CAMERAVIEW_FRAGMENT:
                showCameraViewFragment();
                break;
            default:
                showCameraViewFragment();
                break;
        }
    }

    public void showCameraFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, CameraFragment.newInstance(), null);
        transaction.commit();
    }

    public void showCamera2Fragment(){

    }

    public void showCameraViewFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, CameraViewFragment.newInstance(), null);
        transaction.commit();
    }

    public void showDisplayFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, DisplayFragment.newInstance(), null);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setImageResult(byte[] data){
        imageResult = data;
    }

    public byte[] getImageResult(){
        return imageResult;
    }
}
