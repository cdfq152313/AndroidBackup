package com.addweup.myencrypter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

    AlgorithmParameterSpec mAlgorithmParameterSpec;
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

    private void genAESKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        // AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        secretKey = keyGen.generateKey();
    }

    private void genInitializationVector() throws NoSuchPaddingException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte [] iVAES = new byte[ cipher.getBlockSize() ];
        random.nextBytes(iVAES);
        mAlgorithmParameterSpec = new IvParameterSpec(iVAES);
    }

    private String encrypt(String content) throws BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        // Encode Content in base64
        byte [] contentB64 = Base64.encode(content.getBytes(), Base64.DEFAULT);
        // encrypt
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, mAlgorithmParameterSpec);
        byte [] encrypt = cipher.doFinal(contentB64);
        String encryptStr = Base64.encodeToString(encrypt, Base64.DEFAULT);
        return encryptStr;
    }

    private String decrypt(String content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        // Decode Base64
        byte [] contentB64 = Base64.decode(content, Base64.DEFAULT);
        // decrypt AES
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, mAlgorithmParameterSpec);
        byte [] decodeBytes = cipher.doFinal(contentB64);
        decodeBytes = Base64.decode(decodeBytes, Base64.DEFAULT);
        String str = new String(decodeBytes);
        return str;
    }

    private String encrypt2(String content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, mAlgorithmParameterSpec);
        byte [] temp = cipher.doFinal( content.getBytes() );
        return Base64.encodeToString(temp, Base64.DEFAULT);
    }

    private String decrypt2(String content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte [] temp = Base64.decode(content, Base64.DEFAULT);
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, mAlgorithmParameterSpec);
        byte [] decodeBytes = cipher.doFinal(temp);
        return new String(decodeBytes);
    }

    private void export(){
        String secretKeyB64 = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
        IvParameterSpec iv = (IvParameterSpec) mAlgorithmParameterSpec;
        String vectorB64 = Base64.encodeToString(iv.getIV(), Base64.DEFAULT);

        Log.i("Secret Key Base64", secretKeyB64);
        Log.i("Init Vector Base64", vectorB64);
    }

    public void initialize(View view){
        try {
            long start= System.currentTimeMillis();
            genAESKey();
            genInitializationVector();
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
            word = encrypt2(word);
            long end = System.currentTimeMillis();
            display2.setText("Milliseconds: " + (end-start) );
            result.setText(word);
            Log.i("Encrypt: ", word);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(View view){
        String word = result.getText().toString();
        try {
            long start= System.currentTimeMillis();
            word = decrypt2(word);
            long end = System.currentTimeMillis();
            display3.setText("Milliseconds: " + (end-start) );
            result.setText(word);
            Log.i("Decrypt: ", word);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void export(View view){
        export();
    }
}

