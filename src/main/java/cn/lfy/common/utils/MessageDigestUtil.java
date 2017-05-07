package cn.lfy.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil
{

	public static String getMessageDigest(byte[] buffer, String key)
	{
		MessageDigest digest;
		try
		{
			digest = MessageDigest.getInstance(key);
			digest.update(buffer);
			return bytes2Hex(digest.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String bytes2Hex(byte[] bts)
	{
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++)
		{
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1)
			{
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public static String getMD5(byte[] buffer)
	{
		return getMessageDigest(buffer, "MD5");
	}

	public static String getMD5(String str)
	{
		try
		{
			return getMD5(str.getBytes("ISO-8859-1"));
		}
		catch (UnsupportedEncodingException e)
		{
		}
		return null;
	}

	public static String getSHA1(byte[] buffer)
	{
		return getMessageDigest(buffer, "SHA-1");
	}

	public static String getSHA1(String str)
	{
		try
		{
			return getSHA1(str.getBytes("ISO-8859-1"));
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String getSHA256(byte[] buffer)
	{
		return getMessageDigest(buffer, "SHA-256");
	}

	public static String getSHA256(String str) {
		if (str != null && !"".equals(str)) {
			try {
				return getSHA256(str.getBytes("ISO-8859-1"));
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 文件生成md5
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file)
	{
		InputStream is;
		MessageDigest digest;
		try
		{
			digest = MessageDigest.getInstance("MD5");
			is = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int readNum = 0;
			while ((readNum = is.read(buffer)) > 0)
			{
				digest.update(buffer, 0, readNum);
			}
			is.close();
			return bytes2Hex(digest.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成消息签名
	 * 
	 * @param buffer
	 * @return
	 */
	public static String HashToMD5Hex(byte[] buffer)
	{
		return getMessageDigest(buffer, "MD5");
	}
	
	/**
	 * 生成消息签名
	 * 
	 * @param str
	 * @return
	 */
	public static String HashToMD5Hex(String str)
	{
		try
		{
			return HashToMD5Hex(str.getBytes("utf-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			return null;
		}
	}
}
