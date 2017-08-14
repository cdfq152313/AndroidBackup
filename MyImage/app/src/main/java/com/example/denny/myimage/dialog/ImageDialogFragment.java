package com.example.denny.myimage.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.example.denny.myimage.R;
import com.example.denny.myimage.share.DisplayImageSingleton;

import java.io.ByteArrayOutputStream;

/**
 * Created by denny on 2017/5/29.
 */

public class ImageDialogFragment extends DialogFragment{

    ImageButton imageButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hello");
        builder.setPositiveButton("OK", null);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.image_dialog, null, false);
        imageButton = (ImageButton) view.findViewById(R.id.image);

        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageButton.setImageBitmap(getBitmap());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DisplayImageActivity.class);
                DisplayImageSingleton.getInstance().setBitmap(getBitmap());
                startActivity(intent);
            }
        });
    }

    Bitmap getBitmap(){
        Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.image1);
        return icon;
    }
}
