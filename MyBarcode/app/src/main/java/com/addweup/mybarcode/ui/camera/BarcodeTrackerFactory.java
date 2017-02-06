package com.addweup.mybarcode.ui.camera;

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by cdfq1 on 2016/11/28.
 */

public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private BarcodeGraphicTracker.TrackerListener trackerListener;

    public BarcodeTrackerFactory(BarcodeGraphicTracker.TrackerListener trackerListener) {
        this.trackerListener = trackerListener;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        return new BarcodeGraphicTracker(trackerListener);
    }

}