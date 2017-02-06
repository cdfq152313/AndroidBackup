package com.addweup.mybarcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
    }

    void initComponent(){
        button = (Button) findViewById(R.id.button1);
        textView = (TextView) findViewById(R.id.textView1);
        imageView = (ImageView) findViewById(R.id.imageView1);
    }

    public void click(View view){
        try{
            Bitmap bitmap = getBitmap();
            BarcodeDetector detector = createBarcodeDetector();
            SparseArray<Barcode> barcodes = detect(bitmap, detector);
            decode(barcodes);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    BarcodeDetector createBarcodeDetector() throws Exception{
        BarcodeDetector detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();
        if(!detector.isOperational()){
            throw new Exception("Could not set up the detector!");
        }
        else{
            return detector;
        }
    }

    Bitmap getBitmap(){
        Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.barcode);
        return myBitmap;
    }

    SparseArray<Barcode> detect(Bitmap bitmap, BarcodeDetector detector){
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Barcode> barcodes = detector.detect(frame);
        return barcodes;
    }

    void decode(SparseArray<Barcode> barcodes){
        Barcode thisCode = barcodes.valueAt(0);
        textView.setText(thisCode.rawValue);
    }
}
