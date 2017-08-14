package com.example.denny.myimage.dialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.denny.myimage.R;
import com.example.denny.myimage.share.DisplayImageSingleton;
import com.github.chrisbanes.photoview.PhotoView;

public class DisplayImageActivity extends AppCompatActivity{

    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        photoView = (PhotoView) findViewById(R.id.displayImage);
        Bitmap bitmap = DisplayImageSingleton.getInstance().getBitmap();
        photoView.setImageBitmap(bitmap);
    }
}
