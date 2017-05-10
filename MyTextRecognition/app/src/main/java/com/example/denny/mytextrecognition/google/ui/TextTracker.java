package com.example.denny.mytextrecognition.google.ui;

import android.util.Log;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.text.TextBlock;

/**
 * Created by denny on 2017/5/10.
 */

public class TextTracker extends Tracker<TextBlock> implements MultiProcessor.Factory<TextBlock>{
    Listener trackerListener;

    TextTracker(Listener trackerListener) {
        this.trackerListener = trackerListener;
    }

    /**
     * 第一次在畫面上捕捉到QRCode的時候
     */
    @Override
    public void onNewItem(int id, TextBlock item) {
        trackerListener.onUpdate(item.getValue());
        Log.i("Item", item.getValue());
    }

    /**
     * 只要此QRCode尚未離開鏡頭，使用者如果移動相機位置，讓QRCode在鏡頭中四處晃動，即會觸發此function
     */
    @Override
    public void onUpdate(Detector.Detections<TextBlock> detectionResults, TextBlock item) {

    }

    /**
     * 當QRCode暫時消失時，就會使用此function。
     * (可能因素：突然有幾個frame卡住，突然被東西擋了一下。)
     */
    @Override
    public void onMissing(Detector.Detections<TextBlock> detectionResults) {

    }

    /**
     * 當QRCode永遠在鏡頭中消失時，就會使用此function。
     * 這個function被使用前一定會先觸發onMissing
     * 如果是同一個物件，要等到onDone被觸發後，才會再次觸發onNewItem，不然都是一直觸發onUpdate和onMissing
     */
    @Override
    public void onDone() {

    }

    /**
     * This is a fake Factory, only return itself.
     */
    @Override
    public Tracker<TextBlock> create(TextBlock barcode) {
        return this;
    }

    public interface Listener{
        void onUpdate(String qrcode);
    }
}
