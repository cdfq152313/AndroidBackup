package com.addweup.mybarcode.ui.camera;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by cdfq1 on 2016/11/28.
 */

public class BarcodeGraphicTracker extends Tracker<Barcode> {
    private TrackerListener trackerListener;

    BarcodeGraphicTracker(TrackerListener trackerListener) {
        this.trackerListener = trackerListener;
    }

    /**
     * Start tracking the detected item instance within the item overlay.
     */
    @Override
    public void onNewItem(int id, Barcode item) {

    }

    /**
     * Update the position/characteristics of the item within the overlay.
     */
    @Override
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode item) {
        trackerListener.onUpdate(item.rawValue);
    }

    /**
     * Hide the graphic when the corresponding object was not detected.  This can happen for
     * intermediate frames temporarily, for example if the object was momentarily blocked from
     * view.
     */
    @Override
    public void onMissing(Detector.Detections<Barcode> detectionResults) {

    }

    /**
     * Called when the item is assumed to be gone for good. Remove the graphic annotation from
     * the overlay.
     */
    @Override
    public void onDone() {

    }

    public interface TrackerListener{
        void onUpdate(String qrcode);
    }
}
