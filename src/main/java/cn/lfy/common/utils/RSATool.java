package cn.lfy.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA生成器
 * @author liaopeng
 *
 */
public class RSATool {

	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;
	
	private RSATool(){
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 初始化密钥对生成器，密钥大小为1024位
			keyPairGen.initialize(1024);
			
			KeyPair keyPair = keyPairGen.generateKeyPair();

			privateKey = (RSAPrivateKey) keyPair.getPrivate();

			publicKey = (RSAPublicKey) keyPair.getPublic();
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static RSATool newInstance() {
		return new RSATool();
	}
	
	public String getPrivateKey() {
		return Base64.encode(privateKey.getEncoded());
	}
	
	public String getPublicKey() {
		return Base64.encode(publicKey.getEncoded());
	}
	
	public void savePrivateKey(String fileName) {
		// 生成私钥
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					fileName));
			oos.writeObject(privateKey);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void savePublicKey(String fileName) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fileName));
			oos.writeObject(publicKey);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static RSAPrivateKey getPrivateKey(String fileName) {
		RSAPrivateKey prikey = null;
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(fileName));
			prikey = (RSAPrivateKey) ois.readObject();
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return prikey;
	}
	
	public static RSAPublicKey getPublicKey(String fileName) {
		RSAPublicKey pubkey = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					fileName));
			pubkey = (RSAPublicKey) ois.readObject();
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pubkey;
	}
	
	public static String getPrivateKEY(String fileName) {
		String priKey = Base64.encode(getPrivateKey(fileName).getEncoded());
		return priKey;
	}
	
	public static String getPublicKEY(String fileName) {
		String publicKey = Base64.encode(getPublicKey(fileName).getEncoded());
		return publicKey;
	}
	public static void main(String[] args) throws Exception {

//		String pubfile = "D:\\tmp\\pub.key";
//		String prifile = "D:\\tmp\\pri.key";
		
		String pubfile = "D:\\tmp\\pub_prod.key";
		String prifile = "D:\\tmp\\pri_prod.key";
		
//		RSATool rsaTool = RSATool.newInstance();
//		rsaTool.savePrivateKey(prifile);
//		rsaTool.savePublicKey(pubfile);
		String content = "廖鹏fjsdklfjwklrj23k4j23kl4j23kl4j23kl4j2k3l4jkldsjflkswejr7575w4752475f<>,/:;575w7r52375d47f5s7f523874*&^$%#@!)(*_+{}·~[][][]【】【】【】";
		
		String enContent = RSASignature.encryptByPrivateKey(content, RSATool.getPrivateKEY(prifile));
		System.out.println("加密后：" + enContent);
		
		System.out.println("解密后：" + RSASignature.decryptByPublicKey(enContent, RSATool.getPublicKEY(pubfile)));
		
		System.out.println(RSATool.getPrivateKEY(prifile));
		System.out.println(RSATool.getPublicKEY(pubfile));

	}
}
