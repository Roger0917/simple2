package com.zhgl.module.common.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;









import com.mysql.jdbc.log.Log;
import com.zhgl.util.Des3Util;
import com.zhgl.util.HttpUtil;

@Controller
@Slf4j
@RequestMapping("/ccb")
public class ExchangeRsaController {
	
	@Autowired
	private TransactionController controller;
	@RequestMapping("/mypubkey")
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
			String des = "753890000000479256"+"220181019";
			byte[] real3des = controller.asc2bin(des);
			//对公钥加密
			String encrpRsa = Des3Util.encode3Des(real3des, pubrsa);
			String resp = "000000"+pubrsa;
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
}
