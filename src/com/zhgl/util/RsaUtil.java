package com.zhgl.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;

@Slf4j
public class RsaUtil {

	/**
	 * 随机生成密钥对
	 * @return 
	 */
	public static byte[] genKeyPair() {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(1024,new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 得到私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 得到公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		try {
			// 得到公钥字符串
			log.info("公钥字节位数"+publicKey.getEncoded().length);
			String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
			log.info("公钥字符串"+publicKeyString+"长度"+publicKeyString.length());
			log.info(""+publicKeyString.getBytes().length);
			// 得到私钥字符串
			log.info("私钥字节位数"+privateKey.getEncoded().length);
			String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
			log.info("私钥字符串"+privateKeyString+"长度"+privateKeyString.length());
			log.info(""+"000000".getBytes().length);
			// 将密钥对写入到文件
			/*FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
			FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
			BufferedWriter pubbw = new BufferedWriter(pubfw);
			BufferedWriter pribw = new BufferedWriter(prifw);
			pubbw.write(publicKeyString);
			pribw.write(privateKeyString);
			pubbw.flush();
			pubbw.close();
			pubfw.close();
			pribw.flush();
			pribw.close();
			prifw.close();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return publicKey.getEncoded();
	}
	
	public static void main(String[] args) {
		//RsaUtil.genKeyPair("");
	}

}
