package com.addweup.myencrypter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {
    TextView result;
    TextView display1;
    TextView display2;
    TextView display3;

    SecretKey secretKey;
    Cipher cipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display1 = (TextView) findViewById(R.id.display1);
        display2 = (TextView) findViewById(R.id.display2);
        display3 = (TextView) findViewById(R.id.display3);
        result = (TextView) findViewById(R.id.result);
    }

    private void genAES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // for example
        secretKey = keyGen.generateKey();
    }

    private String encrypt(String content) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        // Encode Content in base64
        byte [] contentB64 = Base64.encode(content.getBytes(), Base64.DEFAULT);
        // encrypt
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte [] encrypt = cipher.doFinal(contentB64);
        String encryptStr = Base64.encodeToString(encrypt, Base64.NO_WRAP);
        return encryptStr;
    }

    private String decrypt(String content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        // Decode Base64
        byte [] contentB64 = Base64.decode(content, Base64.DEFAULT);
        // decrypt AES
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte [] decodeBytes = cipher.doFinal(contentB64);
        decodeBytes = Base64.decode(decodeBytes, Base64.DEFAULT);
        String str = new String(decodeBytes);
        return str;
    }

    public void genAES(View view){
        try {
            long start= System.currentTimeMillis();
            genAES();
            long end = System.currentTimeMillis();
            display1.setText("Milliseconds: " + (end-start) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void encrypt(View view){
        String word = result.getText().toString();
        try {
            long start= System.currentTimeMillis();
            word = encrypt(word);
            long end = System.currentTimeMillis();
            display2.setText("Milliseconds: " + (end-start) );
            result.setText(word);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(View view){
        String word = result.getText().toString();
        try {
            long start= System.currentTimeMillis();
            word = decrypt(word);
            long end = System.currentTimeMillis();
            display3.setText("Milliseconds: " + (end-start) );
            result.setText(word);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

