package com.example.denny.mycamera.camera;


import android.app.Fragment;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.denny.mycamera.CameraActivity;
import com.example.denny.mycamera.R;

public class CameraFragment extends Fragment {


    public CameraFragment() {
        // Required empty public constructor
    }

    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        return fragment;
    }

    Camera mCamera;
    CameraPreview mPreview;

    FrameLayout preview;
    Button captureButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        preview = (FrameLayout) root.findViewById(R.id.camera_preview);
        captureButton = (Button) root.findViewById(R.id.button_capture);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCamera();
    }

    public void initCamera(){
        try{
            // 初始化
            mCamera = Camera.open();
            mPreview = new CameraPreview(getActivity(), mCamera);
            preview.addView(mPreview);

            // 拍照
            captureButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // get an image from the camera
                            mCamera.takePicture(null, null, pictureCallback);
                        }
                    }
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            CameraActivity activity = (CameraActivity) getActivity();
            activity.setImageResult(data);
            activity.showDisplayFragment();
        }
    };

}
