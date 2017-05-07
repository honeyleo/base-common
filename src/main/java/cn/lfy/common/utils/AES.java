package cn.lfy.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.io.BaseEncoding;

@SuppressWarnings("unused")
public class AES 
{
	
	private static final String AES = "AES";

//	private static final String CRYPT_KEY = "JIYHmdjysdk98KJ";
	
	private static final String CRYPT_KEY = "x1YfmdjyFdu68KJ";
	
//	private static final String KEY = "x1YfmdjyFdu68KJ";
	private static final String KEY = "JIYHmdjysdk98Kg";
	
	public static String encrypt(String key, String plain) throws NoSuchAlgorithmException,
    NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
    InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException

    {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec bkey = new SecretKeySpec(makeKey(key), AES);
		cipher.init(Cipher.ENCRYPT_MODE, bkey, cipher.getParameters());
		byte[] plaintxt = cipher.doFinal(plain.getBytes());
		return BaseEncoding.base64().encode(plaintxt);
    }
	
	public static String decrypt(String key, String plain) throws NoSuchAlgorithmException,
    		NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException,
    		InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException 
    {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec bkey = new SecretKeySpec(makeKey(key), AES);
		cipher.init(Cipher.DECRYPT_MODE, bkey, cipher.getParameters());
		byte[] src = BaseEncoding.base64().decode(plain);
		byte[] plaintxt = cipher.doFinal(src);
		return (new String(plaintxt, "UTF-8"));
	}
		
		private static byte[] makeKey(String str) throws UnsupportedEncodingException,
		    NoSuchAlgorithmException 
		{
			byte[] key = new byte[16];
			byte[] key1 = str.getBytes("UTF-8");
			if (key1.length <= key.length) 
			{
			    System.arraycopy(key1, 0, key, 0, key1.length);
			} 
			else 
			{
			    System.arraycopy(key1, 0, key, 0, key.length);
			}
		return key;
	}
		
	public static void main(String[] args) 
	{
		try 
		{
			String encrypt = encrypt(UUID.randomUUID().toString().replaceAll("-", ""), "KJUwlQQcBltSFHr6廖鹏");
			System.out.println(encrypt);
			
//			String decrypt = decrypt(CRYPT_KEY, "v/o/WOW49twPfzgzZIh2l1RY+fAM+OY17C2Xc+huwq4=");
//			System.out.println(decrypt);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}
