package cn.lfy.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 与时间相关的动态口令
 * @author liaopeng
 *
 */
public class ElectronicPassword {

	private static final int[] doubleDigits = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };

	public static int calcChecksum(long num, int digits) {
		boolean doubleDigit = true;
		int total = 0;
		while (0 < digits--) {
			int digit = (int) (num % 10);
			num /= 10;
			if (doubleDigit) {
				digit = doubleDigits[digit];
			}
			total += digit;
			doubleDigit = !doubleDigit;
		}
		int result = total % 10;
		if (result > 0) {
			result = 10 - result;
		}
		return result;
	}

	public static byte[] hmac_sha1(byte[] keyBytes, byte[] text)
			throws NoSuchAlgorithmException, InvalidKeyException {
		// try {
		Mac hmacSha1;
		try {
			hmacSha1 = Mac.getInstance("HmacSHA1");
		} catch (NoSuchAlgorithmException nsae) {
			hmacSha1 = Mac.getInstance("HMAC-SHA-1");
		}
		SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
		hmacSha1.init(macKey);
		return hmacSha1.doFinal(text);
	}

	private static final int[] DIGITS_POWER = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

	public static String generateOTP(byte[] secret, long movingFactor,
			int codeDigits, boolean addChecksum, int truncationOffset)
			throws NoSuchAlgorithmException, InvalidKeyException {
		String result = null;
		int digits = addChecksum ? (codeDigits + 1) : codeDigits;

		byte[] text = new byte[8];
		for (int i = text.length - 1; i >= 0; i--) {
			text[i] = (byte) (movingFactor & 0xff);
			movingFactor >>= 8;
		}

		byte[] hash = hmac_sha1(secret, text);

		int offset = hash[hash.length - 1] & 0xf;

		if ((0 <= truncationOffset) && (truncationOffset < (hash.length - 4))) {
			offset = truncationOffset;
		}
		int binary = ((hash[offset] & 0x7f) << 24)
				| ((hash[offset + 1] & 0xff) << 16)
				| ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

		int otp = binary % DIGITS_POWER[codeDigits];
		if (addChecksum) {
			otp = (otp * 10) + calcChecksum(otp, codeDigits);
		}
		result = Integer.toString(otp);
		while (result.length() < digits) {
			result = "0" + result;
		}
		return result;
	}
	
	public static byte[] SECRET =  hexStringToByte("8F9811E09F06A1EACEDFE2AC2545F40A2515E328");
	
	public static String generateOTP(int codeDigits) {
		long time = System.currentTimeMillis() / 1000;
		double d = Math.floor(time / 300.0);
		time = (long) d;
		try {
			return generateOTP(SECRET, time, 6, false, 16);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String generateOTP() {
		return generateOTP(6);
	}

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeyException {

		if (args == null || args.length == 0) {
			System.out.println("Usage:");
			System.out.println("java testOTP share_secret count");
		}

		byte[] plainText = hexStringToByte("8F9811E09F06A1EACEDFE2AC2545F40A2515E328");
		boolean addChecksum = false;

		for (int i = 1; i < 1000; i++) {
			long time = System.currentTimeMillis() / 1000;
			double d = Math.floor(time / 30.0);
			time = (long) d;
			System.out.println("verify_code:"+ generateOTP(plainText, time, 6, addChecksum, 16));
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}