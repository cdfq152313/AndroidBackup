package com.example.denny.myimage.share;

import android.graphics.Bitmap;

/**
 * Created by denny on 2017/5/29.
 */

public class DisplayImageSingleton {
    private DisplayImageSingleton(){

    }

    static DisplayImageSingleton instance;
    public static DisplayImageSingleton getInstance() {
        if(instance == null){
            instance = new DisplayImageSingleton();
        }
        return instance;
    }

    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
