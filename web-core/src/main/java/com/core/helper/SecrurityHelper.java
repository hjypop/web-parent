package com.core.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.core.support.DesSupport;

public class SecrurityHelper {

	private final static String AES_TYPE = "AES/CBC/PKCS5Padding";

	private final static String AES_IV = "1985042019850420";

	private final static String MINI_BASE = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM0123456789";

	private final static String MINI_RIGHT = "420";

	private final static int MINI_RANDOM = 2;

	private final static int MINI_SUMLENGTH = 3;

	/**
	 * MD5加密
	 * 
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * des算法加密字符串
	 * 
	 * @param sKey
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public static String DesEncryptStr(String sKey, String sInput) throws Exception {

		DesSupport desSupport = new DesSupport(sKey);

		return desSupport.encrypt(sInput);

	}

	/**
	 * des算法解密字符串
	 * 
	 * @param sKey
	 * @param sInput
	 * @return
	 * @throws Exception
	 */
	public static String DesDencryptStr(String sKey, String sInput) throws Exception {
		DesSupport desSupport = new DesSupport(sKey);

		return desSupport.decrypt(sInput);
	}

	/**
	 * 安全MD5加密 该方法会将输入源补上一个字符串然后md5
	 * 
	 * @param sSource
	 * @return
	 */
	public final static String MD5Secruity(String sSource) {
		return MD5Customer(sSource);
	}

	/**
	 * MD5加密自定义
	 * 
	 * @param s
	 * @return
	 */
	public final static String MD5Customer(String s) {
		char hexDigits[] = { 'S', 'R', 'N', 'P', 'A', 'L', 'I', 'U', 'D', 'O', 'B', 'G', 'C', 'D', '3', '1' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 返回32位小写的MD5码
	 */
	public static String getEncoderByMd5(String sessionid)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		StringBuffer hexString = null;
		byte[] defaultBytes = sessionid.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				if (Integer.toHexString(0xFF & messageDigest[i]).length() == 1) {
					hexString.append(0);
				}
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			messageDigest.toString();
			sessionid = hexString + "";
		} catch (NoSuchAlgorithmException nsae) {

		}
		return hexString.toString().toLowerCase();

	}

	public static String sha1(String sInput) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = sInput.getBytes();
		try {

			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);

			byte messageDigest[] = md.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				if (Integer.toHexString(0xFF & messageDigest[i]).length() == 1) {
					hexString.append(0);
				}
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}

			strDes = hexString.toString(); // to HexString
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		}
		return strDes;
	}

	public static String aesEncrypt(String strKey, String strIn) throws Exception {
		SecretKeySpec skeySpec = getKey(strKey);
		Cipher cipher = Cipher.getInstance(AES_TYPE);
		IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(strIn.getBytes());

		return new Base64().encodeAsString(encrypted);
	}

	public static String aesDecrypt(String strKey, String strIn) throws Exception {
		SecretKeySpec skeySpec = getKey(strKey);
		Cipher cipher = Cipher.getInstance(AES_TYPE);
		IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] encrypted1 = new Base64().decode(strIn);

		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	private static SecretKeySpec getKey(String strKey) throws Exception {
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

		return skeySpec;
	}

	/**
	 * 转换一个数字为字符串表示
	 * 
	 * @param sInput
	 *            数字 长度为1-12位
	 * @param iKey
	 *            密钥 长度为3-5位数字
	 * @return
	 */
	public static String miniEncrypt(Long sInput, int iKey) {
		String sReturn = "";

		String sRandom = StringUtils.leftPad(String.valueOf(new Random().nextInt((int) Math.pow(10, MINI_RANDOM))),
				MINI_RANDOM, "9");

		sReturn = FormatHelper.convertFormatNumberBack(
				sRandom + upMiniSum(sRandom + sInput) + String.valueOf(sInput) + String.valueOf(iKey), MINI_BASE);
		return sReturn;

	}

	private static String upMiniSum(String sValue) {
		String sReturn = "";
		int iSum = 0;
		for (char c : sValue.toCharArray()) {
			iSum += Integer.valueOf(c);
		}

		sReturn = StringUtils.leftPad(StringUtils.right(String.valueOf(iSum), MINI_SUMLENGTH), MINI_SUMLENGTH, "0");
		return sReturn;

	}

	public static String miniDecrypt(String sInput, int iKey) {
		String sReturn = "";

		if (StringUtils.isNotBlank(sInput) && StringUtils.containsOnly(sInput, MINI_BASE)) {
			String sNumber = FormatHelper.convertFormatStringNumber(sInput, MINI_BASE);

			if (StringUtils.endsWith(sNumber, String.valueOf(iKey))) {
				System.out.println(sNumber);
				String sRandom = StringUtils.substring(sNumber, 0, MINI_RANDOM);
				String sSum = StringUtils.substring(sNumber, MINI_RANDOM, MINI_RANDOM + MINI_SUMLENGTH);

				sNumber = StringUtils.substringBeforeLast(sNumber.substring(MINI_RANDOM + MINI_SUMLENGTH),
						String.valueOf(iKey));
				System.out.println(sRandom + "#" + sSum + "#" + sNumber);
				if (upMiniSum(sRandom + sNumber).equals(sSum)) {
					sReturn = sNumber;
				}
			}

		}

		return sReturn;
	}

}
