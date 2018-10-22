package com.zhgl.module.common.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.persistence.Convert;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.log.Log;
import com.zhgl.util.Des3Util;
import com.zhgl.util.HttpUtil;

@Controller
@Slf4j
@RequestMapping("/ccb")
public class ExchangeRsaController {
	
	@Autowired
	private static TransactionController controller;
	@RequestMapping(value="/mypubkey",method={RequestMethod.POST})
	public void httpClientResp(String type,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info(type);
		OutputStream outputStream = response.getOutputStream();
		String errormes = "";
		if("pub".equals(type)){
			/*errormes = "000001没有输入参数类型";
			outputStream.write(errormes.getBytes());
	        outputStream.flush();
	        outputStream.close();*/
			//客户公钥
			String pubrsa = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp9GNOVP6GH5MTJKlf19SkvuONnQrJ6wGf9zL+xK2PjM3spu8PQtrOsIQiv4cpHYjleiaLiVIFd6Y9biszQJw96wSGEtr//pIBV+rkcbWcfmRiUitU/r1YT/znwUIdAM7i7Zfkx49Z3Ak1FwG6VxJTliyEoIyJbxYwwOL/rOoKSwIDAQAB";
			//要对公钥加密的3des约定密钥
			String des = "0000479256"+"181022";
			//将普通字符串转换成16进制字符串
			String to16 = str2HexStr(des);
			log.info("转换成16进制"+to16);
			String reto16 = hexStr2Str(to16);
			log.info("转换成原字符串"+reto16);
			//16进制字符串转换成16进制数组
			byte[] real3desbyte = controller.asc2bin(to16);
			log.info("真正的des秘钥字节数组长度"+real3desbyte.length);
		/*	for (byte b : real3desbyte) {
				System.out.println(b);
			}*/
			String read3deString = new String(real3desbyte,"UTF-8");
			System.out.println("真正的des秘钥字符串"+read3deString);
			//字节数组转换为base64字符串
			String base64des = Base64.encodeBase64String(real3desbyte);
			log.info(base64des);
			//des秘钥加密公钥
			String encodersa = Des3Util.encode3Des(base64des.getBytes(), pubrsa);
			log.info("des秘钥加密后的公钥"+encodersa);
			String decodersa = Des3Util.decode3Des(base64des.getBytes(), encodersa);
			log.info("des秘钥解密后的公钥"+decodersa);
			//System.out.println(real3des.length);
			
			String resp = "000000"+encodersa;
			outputStream.write(resp.getBytes());
	        outputStream.flush();
	        outputStream.close();
		}else{
			System.out.println(type);
			errormes = "000001参数类型不正确";
			outputStream.write(errormes.getBytes());
	        outputStream.flush();
	        outputStream.close();
		}
	}
	
	public static void main(String[] args) {
		Map<String, String> map =new HashMap<>();
		map.put("type","pub");
		String result=HttpUtil.doPost("http://localhost:8080/simple/ccb/mypubkey", map);
		log.info(result);
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public String get(){
		return "222";
	}
	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
	    char[] chars = "0123456789ABCDEF".toCharArray();
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();
	    int bit;
	    for (int i = 0; i < bs.length; i++) {
	        bit = (bs[i] & 0x0f0) >> 4;
	        sb.append(chars[bit]);
	        bit = bs[i] & 0x0f;
	        sb.append(chars[bit]);
	        // sb.append(' ');
	    }
	    return sb.toString().trim();
	}
	
	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * @param hexStr
	 * @return
	 */
	public static String hexStr2Str(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    byte[] bytes = new byte[hexStr.length() / 2];
	    int n;
	    for (int i = 0; i < bytes.length; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return new String(bytes);
	}
	
	/*public static void main(String[] args) throws Exception {
		String pubrsa = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp9GNOVP6GH5MTJKlf19SkvuONnQrJ6wGf9zL+xK2PjM3spu8PQtrOsIQiv4cpHYjleiaLiVIFd6Y9biszQJw96wSGEtr//pIBV+rkcbWcfmRiUitU/r1YT/znwUIdAM7i7Zfkx49Z3Ak1FwG6VxJTliyEoIyJbxYwwOL/rOoKSwIDAQAB";
		//要对公钥加密的3des约定密钥
		String des = "0000479256"+"181019";
		//将普通字符串转换成16进制字符串
		String to16 = str2HexStr(des);
		log.info("转换成16进制"+to16);
		String reto16 = hexStr2Str(to16);
		log.info("转换成原字符串"+reto16);
		//16进制字符串转换成16进制数组
		byte[] real3desbyte = controller.asc2bin(to16);
		log.info("真正的des秘钥字节数组长度"+real3desbyte.length);
		for (byte b : real3desbyte) {
			System.out.println(b);
		}
		String read3deString = new String(real3desbyte,"UTF-8");
		System.out.println("真正的des秘钥字符串"+read3deString);
		//字节数组转换为base64字符串
		String base64des = Base64.encodeBase64String(real3desbyte);
		log.info(base64des);
		//des秘钥加密公钥
		String encodersa = Des3Util.encode3Des(base64des.getBytes(), pubrsa);
		log.info("des秘钥加密后的公钥"+encodersa);
		String decodersa = Des3Util.decode3Des(base64des.getBytes(), encodersa);
		log.info("des秘钥解密后的公钥"+decodersa);
		//System.out.println(real3des.length);
		
	}*/
	

	
}