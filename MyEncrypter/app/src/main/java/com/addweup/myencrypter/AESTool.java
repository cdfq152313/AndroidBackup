package com.addweup.myencrypter;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by denny on 2017/5/25.
 */

public class AESTool {

    SecureRandom random = new SecureRandom();

    public SecretKey genAESKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        // AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        return keyGen.generateKey();
    }

    public IvParameterSpec genIV() throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte [] iVAES = new byte[ cipher.getBlockSize() ];
        random.nextBytes(iVAES);
        return new IvParameterSpec(iVAES);
    }

    public String encrypt(String content, SecretKey secretKey, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte [] temp = cipher.doFinal( content.getBytes() );
        return Base64.encodeToString(temp, Base64.DEFAULT);
    }

    public String decrypt(String content, SecretKey secretKey, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte [] temp = Base64.decode(content, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte [] decodeBytes = cipher.doFinal(temp);
        return new String(decodeBytes);
    }

    public String secretKeyToBase64(SecretKey secretKey){
        return Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
    }

    public String ivToBase64(IvParameterSpec iv){
        return Base64.encodeToString(iv.getIV(), Base64.DEFAULT);
    }

    public SecretKey base64ToSecretKey(String base64Key){
        byte [] key = Base64.decode(base64Key, Base64.DEFAULT);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
}
