package com.zhgl.module.common.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhgl.util.Des3Util;
import com.zhgl.util.HttpUtil;
import com.zhgl.util.RsaUtil;

@Controller
@Slf4j
@RequestMapping("/dcp")
public class ExchangeRsaController {

	@RequestMapping(value="/open/pubkey",method={RequestMethod.POST})
	public void httpClientResp(String type,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info(type);
		OutputStream outputStream = response.getOutputStream();
		String errormes = "";
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		if(type != null && type.length() != 0) {
			if("pub".equals(type)){
				//客户公钥byte[]
				byte [] rsa = RsaUtil.genKeyPair();
				//要对公钥加密的3des约定密钥
				String des = "9020510801"+ getDate();
				//将普通字符串转换成16进制字符串
				String to16 = str2HexStr(des);
				log.info("转换成16进制"+to16);
				String reto16 = hexStr2Str(to16);
				log.info("转换成原字符串"+reto16);
				
				//16进制字符串转换成16进制秘钥数组
				byte[] real3desbyte = asc2bin(to16);
				log.info("真正的秘钥数组长度"+real3desbyte.length);
				String read3deString = new String(real3desbyte,"UTF-8");
				log.info("真正的des秘钥字符串"+read3deString);
				//字节数组转换为base64字符串
				String base64des = Base64.encodeBase64String(real3desbyte);
				log.info("真正的Base64秘钥字符串"+base64des);
				//des秘钥加密公钥
				byte[] encodersa = Des3Util.encode3Des(base64des.getBytes(), rsa);
				log.info("des秘钥加密后的公钥长度"+encodersa.length);
				String encodersastr = Base64.encodeBase64String(encodersa);
				log.info("des秘钥加密后的公钥base64字符串"+ encodersastr);
			
				byte[] resphead ="000000".getBytes();
				log.info("返回的字节流头长度"+resphead.length+"返回的字节流加密后的公钥长度"+encodersa.length);

				byte[]decodersa = Des3Util.decode3Des(Base64.encodeBase64String(real3desbyte).getBytes(), encodersa);
				log.info("解密后的公钥字节流长度"+decodersa.length);
				String decodersastr = Base64.encodeBase64String(decodersa);
				log.info("解密后的公钥base64字符串"+decodersastr);
				byte[] aa = Base64.decodeBase64(decodersastr);
				log.info(""+Arrays.equals(decodersa, aa));
				outputStream.write(resphead);
				outputStream.write(encodersastr.getBytes());
		        outputStream.flush();
		        outputStream.close();
			}else{
				System.out.println(type);
				errormes = "000001 parameter is incorrect";
				outputStream.write(errormes.getBytes());
		        outputStream.flush();
		        outputStream.close();
			}
		}else{
			System.out.println(type);
			errormes = "000002 parameter is null";
			outputStream.write(errormes.getBytes());
	        outputStream.flush();
	        outputStream.close();
		}
			
	}
	
	public static void main(String[] args) {
		Map<String, String> map =new HashMap<>();
		map.put("type","pub");
		String result=HttpUtil.doPost("http://localhost:8080/simple/dcp/open/pubkey", map);
		log.info(result);
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
	
	/**
	 * 16进制字符串转数组
	 * @param hexString
	 * @return
	 */
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
	
	/**
	 * 获得当前日期后6位 
	 * @return
	 */
	public static String getDate(){
		  Date d = new Date();  
	      System.out.println(d);  
	      SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");  
	      String dateNowStr = sdf.format(d);  
	      System.out.println("格式化后的日期：" + dateNowStr);
	      String dateNowStrsplit = dateNowStr.replaceAll("[[\\s-:punct:]]","");
	      System.out.println("格式化后的日期：" + dateNowStrsplit); 
	      return dateNowStrsplit;
	}
	
	
	
}



