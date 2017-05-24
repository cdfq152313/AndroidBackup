package com.example.denny.mycamera.display;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denny.mycamera.CameraActivity;
import com.example.denny.mycamera.R;

import java.io.ByteArrayOutputStream;

public class DisplayFragment extends Fragment {

    TextView message;
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
        message = (TextView) view.findViewById(R.id.message);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
//        display();
        displayCompress();
    }

    public void display(){
        CameraActivity activity = (CameraActivity) getActivity();
        byte[] data = activity.getImageResult();

        Bitmap bImage = Tool.getSmallBitmap(data);
        display.setImageBitmap(bImage);
    }

    public void displayCompress(){
        CameraActivity activity = (CameraActivity) getActivity();
        byte[] data = activity.getImageResult();
        byte [] compress = Tool.bitmapToByte(Tool.getSmallBitmap(data));

        Bitmap bImage = BitmapFactory.decodeByteArray(compress, 0, compress.length);
        display.setImageBitmap(bImage);

        showDiffrence(data, compress);
    }

    void showDiffrence(byte [] oldData, byte [] newData){
        message.setText(
                String.format("Original Length: %dKB\nCompress Length: %dKB",
                    oldData.length/1024, newData.length/1024)
        );
    }

}
