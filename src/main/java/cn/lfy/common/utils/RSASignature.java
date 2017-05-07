/**
 * 
 */
package cn.lfy.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSASignature{
	
	/**

	* 解密

	* @param content 密文
	* 
	* @param key 商户私钥

	* @return 解密后的字符串

	*/
	public static String decrypt(String content, String key) throws Exception {
        PrivateKey prikey = getPrivateKey(key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), "utf-8");
    }


	
	/**

	* 得到私钥

	* @param key 密钥字符串（经过base64编码）

	* @throws Exception

	*/

	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64.decode(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;

	}
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param encode 字符集编码
	* @return 签名值
	*/
	public static String sign(String content, String privateKey,String encode)
	{
		String charset = "utf-8";
		if(encode != null && !"".equals(encode)){
		    charset=encode;
		}
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param publicKey 支付宝公钥
	* @param encode 字符集编码
	* @return 布尔值
	*/
	public static boolean doCheck(String content, String sign,
			String publicKey, String encode) throws Exception{
		String charset = "utf-8";
		if (encode != null && !"".equals(encode)) {
			charset = encode;
		}
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] encodedKey = Base64.decode(publicKey);
		PublicKey pubKey = keyFactory
				.generatePublic(new X509EncodedKeySpec(encodedKey));

		java.security.Signature signature = java.security.Signature
				.getInstance(SIGN_ALGORITHMS);

		signature.initVerify(pubKey);
		signature.update(content.getBytes(charset));

		boolean bverify = signature.verify(Base64.decode(sign));
		return bverify;
	}
	
	 /**  
     * 加密<br>  
     * 用公钥加密  
     *    
     * @param data  
     * @param key  
     * @return  
     * @throws Exception  
     */   
    public static byte[] encrypt(byte[] data, String key)     
            throws Exception {     
        // 对公钥解密     
        byte[] keyBytes = Base64.decode(key);     
  
        // 取得公钥     
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);     
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");     
        Key publicKey = keyFactory.generatePublic(x509KeySpec);     
  
        // 对数据加密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());     
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);     
  
        return cipher.doFinal(data);     
    }

    /**
     * 加密<br>  
     * 用公钥加密  
     * @param content
     * @param key
     * @return
     */
    public static String encrypt(String content, String key) {
    	try {
			byte[] data = content.getBytes("utf-8");
			byte[] enData = encrypt(data, key);
			String enContent = Base64.encode(enData);
			return enContent;
		} catch (Exception e) {
			System.err.println("encrypt error");
		}
    	return null;
    }
    
    /**
     * 加密
     * 用私钥加密数据
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String key) throws Exception {
    	byte[] bytes = encryptByPrivateKey(data.getBytes("UTF-8"),key);
    	String enString = Base64.encode(bytes);
    	return enString;
    }
    
    /**
     * 加密
     * 用私钥加密数据
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {

		byte[] keyBytes;

		keyBytes = Base64.decode(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		InputStream ins = new ByteArrayInputStream(data);
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要加密的内容，按64位拆开解密
		byte[] buf = new byte[64];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}
		return writer.toByteArray();
	}

	/**
	 * 加密 用公钥加密数据
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, String key)
			throws Exception {
		byte[] bytes = encryptByPublicKey(data.getBytes("UTF-8"), key);
		String enString = Base64.encode(bytes);
		return enString;
	}

	/**
	 * 加密 用公钥加密数据
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {

		byte[] keyBytes;

		keyBytes = Base64.decode(key);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		InputStream ins = new ByteArrayInputStream(data);
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要加密的内容，按64位拆开解密
		byte[] buf = new byte[64];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}
		return writer.toByteArray();
	}

	/** */
	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = Base64.decode(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		// 解密时超过128字节就报错。为此采用分段解密的办法来解密
		InputStream ins = new ByteArrayInputStream(data);
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按64位拆开解密
		byte[] buf = new byte[128];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}
		return writer.toByteArray();
	}

	/** */
	/**
	 * 解密<br>
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String data, String key)
			throws Exception {
		byte[] bytes = decryptByPublicKey(Base64.decode(data), key);
		String deString = new String(bytes, "UTF-8");
		return deString;
	}

	/** */
	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data, String key)
			throws Exception {
		byte[] bytes = decryptByPrivateKey(Base64.decode(data), key);
		String deString = new String(bytes, "UTF-8");
		return deString;
	}

	/** */
	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = Base64.decode(key);

		// 取得公钥
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateKey = keyFactory.generatePrivate(keySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		// 解密时超过128字节就报错。为此采用分段解密的办法来解密
		InputStream ins = new ByteArrayInputStream(data);
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按64位拆开解密
		byte[] buf = new byte[128];
		int bufl;

		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}

			writer.write(cipher.doFinal(block));
		}
		return writer.toByteArray();
	}

	public static void main(String args[]) {
    	
    	String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOCy+9WBnF+vdBSm2xzE2t0kZ9YYOgyH7OrqQKvDRRGBsnOurcpqxqTw/dWD00nnxaLCYm4TViLePbv7GmGLCC5/AppHcOMUI7tPTanET/ds7oXKDBl26T5qrjV7YzepC11eiv0vydeq6pYQMNY06mh+gIx1vprMgfJ9O4uPlsa/AgMBAAECgYEA0uAGqhQOblpaBXumTPSONlYRQQLRj6sM9FZNxSToMArZL+nyXfIhZPBqODSUWVqzxxDIkWeCQgzyQPXih0KUtFMjmejZZQ8fnsn4QKtn5VcEICB9svPl9mV2HOJE8w1+YAb6LnExWGhkqX0Rfmc/NxUWRPpb2Jp+RrFS+hEF/1ECQQD/H9nN4FHulBISV1J4/vqqt/FHk8tjzBiROnXG7AzN054De5+pvPAnnDjQIVXskgrYaPIW6XduQ9B8RLVxSrx5AkEA4Xhm039aLG4kivAckOtmaOSsrFWvgsEiuMfJpJD8DdIel0qQPlCPNzttLAk1wAH21ND5KdKdsQSkk1xXAtre9wJBAPLUszA1QmbugyD4AqTuNVNHT53Me/waaIva8/2J8kn7EVzJT1h1YLSjCqR4BlvtSetgQIU0ZRwL6iZOE9GozCkCQQCmfgwgHShvKp4dYpUVhl5HEqVXVKaXvRpwrEcAGHenlQM14L+G5GH8/Qruu5ZPRKQKHo3/DiGXt7/3ePZfi1OdAkBIelUxluzzz2YhpAuUxAogK5ooPtm3XpZh137uUdl4UgB6KXZItHNQCoYxW/EezYckogWvMsUfq6frL0XiJflp";
    	String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgsvvVgZxfr3QUptscxNrdJGfWGDoMh+zq6kCrw0URgbJzrq3Kasak8P3Vg9NJ58WiwmJuE1Yi3j27+xphiwgufwKaR3DjFCO7T02pxE/3bO6FygwZduk+aq41e2M3qQtdXor9L8nXquqWEDDWNOpofoCMdb6azIHyfTuLj5bGvwIDAQAB";
    	
    	String content = "name=廖鹏sdfaslkdjflksadjflksjfdklsajdlfkjaskl的风景阿雷克斯的九分裤拉萨的减肥了凯撒的九分裤了撒娇的疯了凯撒的减肥昆仑山的飓风桑迪的说法就算了肯定烦精神科的减肥了开始的减肥了卡死的减肥了开始的减肥凯瑟琳的减肥了盛开的风景死定了看风景了可是大风景撒辽阔的1213131232312312421333333333333333333333333顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶rtwerwerwerewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww";
    	
    	try {
			System.out.println("#####################code#############################");
			String enString = encryptByPrivateKey(content, privateKey);
			System.out.println("加密后：" + enString);
			
			String deString = decryptByPublicKey(enString, publicKey);
			System.out.println("解密后：" + deString);
			
			System.out.println("#####################code#############################");
			String pString = encryptByPublicKey(content, publicKey);
			System.out.println("公钥加密后的数据：" + pString);
			
			String dString = decryptByPrivateKey(pString, privateKey);
			System.out.println("私钥解密出来的数据：" + dString);
			System.out.println("#####################code#############################");
			
			System.out.println("#####################alipay#############################");
			content = "name=廖鹏-liaopeng";
			enString = encrypt(content, publicKey);
			System.out.println("公钥加密后的数据：" + enString);
			System.out.println("私钥解密出来的数据：" + decrypt(enString, privateKey));
			System.out.println("#####################alipay#############################");
			String cccc = decryptByPublicKey("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", publicKey);
			System.out.println(cccc);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
