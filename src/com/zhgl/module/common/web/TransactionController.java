package com.zhgl.module.common.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.EscapeBodyTag;
import org.w3c.dom.Document;

import com.zhgl.util.Des3Util;
import com.zhgl.util.HttpConnectionUtil;

@RestController
@RequestMapping("/cb")
@Slf4j
public class TransactionController {
	
	//@Autowired
	//private TranscationService transcationService;
	/**
	 * 签到接口
	 * @param uid
	 * @param date
	 * @return
	 */
	@RequestMapping("/signin")
	public String signin(String uid,String date){
		
		return "签到成功";
	}
	
	/**
	 * 签退接口
	 * @param uid
	 * @param date
	 * @return
	 */
	@RequestMapping("/signoff")
	public String singOff(String uid,String date){
		
		return "签退成功";
	}
	
	/**
	 * 账户登记查询接口
	 * @param args
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	/*@RequestMapping("/findaccountreg")
	public String findAccountReg(String chanl_cust_no) throws UnsupportedEncodingException {
		String content = "chanl_cust_no="+chanl_cust_no+"&xml="+xml+"&signature="+;
		String postUrl = "http://ip:port/interlink/UploadFile";
		Charset charset = Charset.forName("UTF-8")
		//发送请求报文,获得响应报文
		HttpConnectionUtil.transferData(content, charset, postUrl);
	}*/

	
	@RequestMapping("/download")//请求路径
    public void downloadResource(@RequestParam(value = "fileName", required = true) String fileName, HttpServletResponse response) {
        String dataDirectory = "C:\\Users\\giga\\Desktop";//文件所在目录
        Path file = Paths.get(dataDirectory, fileName);//文件对象
        if (Files.exists(file)) {
            response.setContentType("application/x-gzip");
            try {
                response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            
            Files.copy(file, response.getOutputStream());//以输出流的形式对外输出提供下载
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
	
	@RequestMapping("/mypubkey")
	public void prodiveRsa(HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("进入服务端响应方法");
		 //接收请求参数
	    InputStreamReader  reader=new InputStreamReader(request.getInputStream());
	    BufferedReader buffer=new BufferedReader(reader);
	    String data=buffer.readLine();
	    System.out.println(data);

		String type = request.getParameter("type");
		log.info("type="+type);
		OutputStream outputStream = response.getOutputStream();
		String errormes = "";
		if(type==null){
			errormes = "000001没有输入参数类型";
			outputStream.write(errormes.getBytes());
	        outputStream.flush();
	        outputStream.close();
		}
		if(type!="pub"){
			errormes = "000002参数类型不正确";
			outputStream.write(errormes.getBytes());
	        outputStream.flush();
	        outputStream.close();
		}else{
			//客户公钥
			String pubrsa = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp9GNOVP6GH5MTJKlf19SkvuONnQrJ6wGf9zL+xK2PjM3spu8PQtrOsIQiv4cpHYjleiaLiVIFd6Y9biszQJw96wSGEtr//pIBV+rkcbWcfmRiUitU/r1YT/znwUIdAM7i7Zfkx49Z3Ak1FwG6VxJTliyEoIyJbxYwwOL/rOoKSwIDAQAB";
			//要对公钥加密的3des约定密钥
			String des = "753890000000479256"+"220181019";
			byte[] real3des = asc2bin(des);
			//对公钥加密
			String encrpRsa = Des3Util.encode3Des(real3des, pubrsa);
			String resp = "000000"+encrpRsa;
			outputStream.write(resp.getBytes());
	        outputStream.flush();
	        outputStream.close();
		}
	}
	public static void main(String[] args) throws IOException {
		// -------------企业请求银行接口 获得3des密钥和银行公钥
		/*String bianhao = "SC51000009020510801";
		String content = "chanl_cust_no="+bianhao+"&type=des";
		//Charset charset = Charset.forName("UTF-8");
		String postUrl = "http://124.127.94.46:8181/interlink/KeyTransfer";
		String string = HttpConnectionUtil.transferData(content, postUrl);
		log.info(string);*/
		
		//-------------银行请求企业获得企业公钥
		String postUrl = "http://127.0.0.1:8080/simple/ccb/mypubkey";
		String content = "type=pub";
		String string = HttpConnectionUtil.transferData(content, postUrl);
		System.out.println("响应的string"+string);
		log.info(string);
	}
}
