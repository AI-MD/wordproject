package com.project.word.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.project.word.data.KeyAndIV;

public class CryptoUtil {
    public static String encrypt(SecretKey skeySpec, byte[] iv, String value) {
        try {
        	IvParameterSpec ivSpec = new IvParameterSpec(iv/*initVector.getBytes("UTF-8")*/);
            //SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

            //byte[] encrypted = cipher.doFinal(value.getBytes());
            //System.out.println("encrypted string: "
            //        + Base64.encodeBase64String(encrypted));
            String encrypted = Base64.encodeBase64String(cipher.doFinal(
            		value.getBytes("UTF-8")));
            return encrypted;
            //return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(SecretKey skeySpec, byte[] iv, String encrypted) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(iv/*initVector.getBytes("UTF-8")*/);
            //SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    public static KeyAndIV autogenerateKeyAndIV(int keylen, String ivalgorithm, int ivlen){
    	
    	KeyAndIV data = null;
		try {
			data = new KeyAndIV(keylen,ivalgorithm,ivlen);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return data; 
		
    }

    //public static void main(String[] args) {
    //    String key = "Bar12345Bar12345"; // 128 bit key
    //    String initVector = "RandomInitVector"; // 16 bytes IV

    //    System.out.println(decrypt(key, initVector,
    //            encrypt(key, initVector, "Hello World")));
    //}
}
