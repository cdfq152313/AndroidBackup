package com.addweup.myencrypter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class MainActivity extends AppCompatActivity {
    TextView result;
    TextView display1;
    TextView display2;
    TextView display3;
    TextView display4;

    AESTool aesTool;

    SecretKey secretKey;
    IvParameterSpec iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aesTool = new AESTool();
        display1 = (TextView) findViewById(R.id.display1);
        display2 = (TextView) findViewById(R.id.display2);
        display3 = (TextView) findViewById(R.id.display3);
        display4 = (TextView) findViewById(R.id.display4);
        result = (TextView) findViewById(R.id.result);
    }

    public void initialize(View view){
        try {
            long start= System.currentTimeMillis();
            secretKey = aesTool.genAESKey();
            iv = aesTool.genIV();
            long end = System.currentTimeMillis();
            display1.setText("Milliseconds: " + (end-start) );
        } catch (Exception e) {
            Toast.makeText(this, "Generate Key Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void encrypt(View view){
        String word = result.getText().toString();
        try {
            long start= System.currentTimeMillis();
            word = aesTool.encrypt(word, secretKey, iv);
            long end = System.currentTimeMillis();
            display2.setText("Milliseconds: " + (end-start) );
            result.setText(word);
            Log.i("Encrypt", word);
        } catch (Exception e) {
            Toast.makeText(this, "Encrypt Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void decrypt(View view){
        String word = result.getText().toString();
        try {
            long start= System.currentTimeMillis();
            word = aesTool.decrypt(word, secretKey, iv);
            long end = System.currentTimeMillis();

            display3.setText("Milliseconds: " + (end-start) );
            result.setText(word);
            Log.i("Decrypt", word);
        } catch (Exception e) {
            Toast.makeText(this, "Decrypt Failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void exportKey(View view){
        String secretKeyB64 = aesTool.secretKeyToBase64(secretKey);
        String vectorB64 = aesTool.ivToBase64(iv);

        display4.setText(secretKeyB64);
        Log.i("Export", String.format("SecretKey B64: %s", secretKeyB64));
        Log.i("Export", String.format("IV B64: %s", vectorB64));
    }

    public void importKey(View view){
        String secretKeyB64 = display4.getText().toString();
        secretKey = aesTool.base64ToSecretKey(secretKeyB64);
        Toast.makeText(this, "Read Successfully", Toast.LENGTH_SHORT).show();
    }
}

