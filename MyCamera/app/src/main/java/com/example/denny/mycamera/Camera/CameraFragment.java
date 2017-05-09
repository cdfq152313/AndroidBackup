package com.example.denny.mycamera.Camera;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.denny.mycamera.MainActivity;
import com.example.denny.mycamera.R;
import com.google.android.cameraview.CameraView;

public class CameraFragment extends Fragment {

    CameraView cameraView;
    View button;

    public CameraFragment() {
        // Required empty public constructor
    }

    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
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
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
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
                MainActivity activity = (MainActivity) getActivity();
                activity.setImageResult(data);
                activity.showDisplayFragment();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Picture take", Toast.LENGTH_SHORT).show();
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
