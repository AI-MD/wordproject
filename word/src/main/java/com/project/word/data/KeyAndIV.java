package com.project.word.data;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyAndIV {

	SecretKey key;
	byte[] iv;
	
	public KeyAndIV(int keylen, String ivalgorithm, int ivlen) 
			throws NoSuchAlgorithmException{
		
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(keylen);
		key = kgen.generateKey();
		
		
		iv = new byte[ ivlen ];
		SecureRandom random = SecureRandom.getInstance( ivalgorithm );
		random.nextBytes( iv );
		
	}
	

	public SecretKey getKey() {
		return key;
	}
	public void setKey(SecretKey key) {
		this.key = key;
	}


	public byte[] getIv() {
		return iv;
	}
	public void setIv(byte[] iv) {
		this.iv = iv;
	}
	
}
