package com.zhgl.module.common.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		if("pub".equals(type)){
			/*errormes = "000001没有输入参数类型";
			outputStream.write(errormes.getBytes());
	        outputStream.flush();
	        outputStream.close();*/
			//客户公钥byte[]
			//String pubrsa = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp9GNOVP6GH5MTJKlf19SkvuONnQrJ6wGf9zL+xK2PjM3spu8PQtrOsIQiv4cpHYjleiaLiVIFd6Y9biszQJw96wSGEtr//pIBV+rkcbWcfmRiUitU/r1YT/znwUIdAM7i7Zfkx49Z3Ak1FwG6VxJTliyEoIyJbxYwwOL/rOoKSwIDAQAB";
			byte [] rsa = RsaUtil.genKeyPair();
			//要对公钥加密的3des约定密钥
			String des = "9020510801"+"181022";
			//将普通字符串转换成16进制字符串
			String to16 = str2HexStr(des);
			log.info("转换成16进制"+to16);
			String reto16 = hexStr2Str(to16);
			log.info("转换成原字符串"+reto16);
			//16进制字符串转换成16进制数组
			byte[] real3desbyte = asc2bin(to16);
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
			byte[] encodersa = Des3Util.encode3Des(Base64.encodeBase64String(real3desbyte), rsa);
			log.info("des秘钥加密后的公钥长度"+encodersa.length);
			//String decodersa = Des3Util.decode3Des(base64des.getBytes(), encodersa);
			//log.info("des秘钥解密后的公钥"+decodersa);
			//System.out.println(real3des.length);
			byte[] resphead ="000000".getBytes();
			byte[] resp = unitByteArray(resphead, encodersa);
			log.info("返回的字节流长度"+resp.length);
			String respString = Base64.encodeBase64String(resp);
			log.info("合并的字节流转字符串"+respString);
			Des3Util.decode3Des(Base64.encodeBase64String(real3desbyte).getBytes(), encodersa);
			outputStream.write(respString.getBytes());
	        outputStream.flush();
	        outputStream.close();
		}else{
			System.out.println(type);
			errormes = "000001 parameter is incorrect";
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
     * 合并byte数组
     */
    public static byte[] unitByteArray(byte[] byte1,byte[] byte2){
        byte[] unitByte = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
        return unitByte;
    }

	
}



