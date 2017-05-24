package com.example.denny.mycamera.cameraview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.denny.mycamera.CameraActivity;
import com.example.denny.mycamera.R;
import com.google.android.cameraview.CameraView;

public class CameraViewFragment extends Fragment {

    CameraView cameraView;
    View button;

    public CameraViewFragment() {
        // Required empty public constructor
    }

    public static CameraViewFragment newInstance() {
        CameraViewFragment fragment = new CameraViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_view, container, false);
        cameraView = (CameraView) view.findViewById(R.id.camera_view);
        button = view.findViewById(R.id.button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cameraView.addCallback(new CameraView.Callback() {
            @Override
            public void onPictureTaken(CameraView cameraView, byte[] data) {
                CameraActivity activity = (CameraActivity) getActivity();
                activity.setImageResult(data);
                activity.showDisplayFragment();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.takePicture();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        cameraView.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        cameraView.stop();
    }
}
