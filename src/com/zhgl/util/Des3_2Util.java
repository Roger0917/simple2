package com.zhgl.util;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;

import com.mysql.jdbc.log.Log;

@Slf4j
public class Des3_2Util{
    /*
* 加密
*/
    public static byte[] EncryptString(byte[] t, byte[] bkey) {
        // MD5加密
     //   String md5s = EncryptMD5.getMD5(strText);
        try {
            SecureRandom random = new SecureRandom();
           // byte[] bkey  =(sKey.substring(0,8)).getBytes();
            DESKeySpec deskey = new DESKeySpec(bkey);
            // 创建一个密钥工厂，然后用它把DESKeyspec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(deskey);
            // cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
 
           // cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // 用密钥初化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
         //   // 正式执行加密操作
           // String str = md5s + strText;
            //byte[] t = strText.getBytes("UTF-8");
           byte[] bResult = cipher.doFinal(t);
           return bResult;
            // 1、加密完byte[] 后，需要将加密了的byte[] 转换成base64保存，如：
            // BASE64Encoder base64encoder = new BASE64Encoder();
            // String encode=base64encoder.encode(bytes)；
           // return Base64.encodeBase64String(bResult);
 
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public static void main(String[] args) {
    	String des = "9020510801"+ "181023";
		//约定密钥转换成16进制秘钥数组
		byte[] real3desbyte = asc2bin(des);
		log.info(""+EncryptString(RsaUtil.genKeyPair(), real3desbyte).length);
		String resultString =Base64.encodeBase64String(EncryptString(RsaUtil.genKeyPair(), real3desbyte)); 
		log.info(resultString);
		
		String decode = Base64.encodeBase64String(DecryptString(resultString, real3desbyte));
		log.info(decode);
		
	}
	public static byte[] asc2bin(String hexString) {
		byte[] hexbyte = hexString.getBytes();
		byte[] bitmap = new byte[hexbyte.length / 2];
		for (int i = 0; i < bitmap.length; i++) {
		hexbyte[i * 2] -= hexbyte[i * 2] > '9' ? 7 : 0;
		hexbyte[i * 2 + 1] -= hexbyte[i * 2 + 1] > '9' ? 7 : 0;
		bitmap[i] = (byte) ((hexbyte[i * 2] << 4 & 0xf0) | (hexbyte[i * 2 + 1] & 0x0f));
		}
		return bitmap;
	
	}
    /*
    * 解密
    */
    public static byte[] DecryptString(String strText, byte[] bkey) {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DesKeySpec对象
        //byte[] bkey = (sKey.substring(0,8)).getBytes();
        DESKeySpec desKey = null;
        try {
            desKey = new DESKeySpec(bkey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        // 创建一个密钥工厂
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey secreKey = null;
        try {
            secreKey = keyFactory.generateSecret(desKey);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        // Cipher对象实际完成解密操作
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        // 用密钥初始化Cipher对象
        try {
            cipher.init(Cipher.DECRYPT_MODE, secreKey, random);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        // 真正开始解密
        // 2、解密前，需要将加密后的字符串从base64转回来再解密，如：
         //BASE64Decoder base64decoder = new BASE64Decoder();
        // byte[] encodeByte = base64decoder.decodeBuffer(s);
 
        byte[] encodeByte;
        byte[] b;
        try {
            encodeByte = Base64.decodeBase64(new String( strText.getBytes(),"UTF-8"));
            b = cipher.doFinal(encodeByte);
           // String s = new String(b, "UTF-8");
            //String s = Base64.encodeBase64String(b);
            return b;
 
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}