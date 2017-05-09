package com.example.denny.mycamera.Display;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.denny.mycamera.MainActivity;
import com.example.denny.mycamera.R;

public class DisplayFragment extends Fragment {

    ImageView display;
    Button button;

    public DisplayFragment() {
        // Required empty public constructor
    }

    public static DisplayFragment newInstance() {
        DisplayFragment fragment = new DisplayFragment();
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
        View view = inflater.inflate(R.layout.fragment_display, container, false);
        display = (ImageView) view.findViewById(R.id.display_view);
        button = (Button) view.findViewById(R.id.button);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.showCameraFragment();
            }
        });
        display();
    }

    public void display(){
        MainActivity activity = (MainActivity) getActivity();
        byte[] data = activity.getImageResult();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bImage = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        display.setImageBitmap(bImage);
    }
}
