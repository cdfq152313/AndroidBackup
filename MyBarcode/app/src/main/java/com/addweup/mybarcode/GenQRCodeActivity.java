package com.addweup.mybarcode;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.UUID;

public class GenQRCodeActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_qrcode);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void genQRCodeClick(View view){
        String text = String.format("Hello UUID: %s", UUID.randomUUID());
        Bitmap bitmap = encodeAsBitmap(text);
        imageView.setImageBitmap(bitmap);
    }

    public Bitmap encodeAsBitmap(String str){
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 250, 250);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
            bitmap = cropBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap cropBitmap(Bitmap bitmap){
        int startxy = 20;
        int sidelength = bitmap.getWidth() - (startxy*2);
        return Bitmap.createBitmap(bitmap, startxy, startxy, sidelength, sidelength);
    }
}
