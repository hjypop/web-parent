package com.hjy.base.support;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DesSupport {
	private byte[] desKey;

	public DesSupport(String desKey) {
		this.desKey = desKey.getBytes();
	}

	public byte[] desEncrypt(byte[] plainText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	public byte[] desDecrypt(byte[] encryptText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	public String encrypt(String input) throws Exception {
		return base64Encode(desEncrypt(input.getBytes()));
	}

	public String decrypt(String input) throws Exception {
		byte[] result = base64Decode(input);
		return new String(desDecrypt(result));
	}

	public static String base64Encode(byte[] s) {
		if (s == null)
			return null;

		Base64 base64 = new Base64();

		return base64.encodeAsString(s);
	}

	public static byte[] base64Decode(String s) {
		if (s == null)
			return null;
		Base64 base64 = new Base64();

		byte[] b = base64.decode(s);
		return b;
	}
}
