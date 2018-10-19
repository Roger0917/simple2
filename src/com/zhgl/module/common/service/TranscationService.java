/*package com.zhgl.module.common.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import org.springframework.stereotype.Service;

@Service
public class TranscationService {
	
	 public String getXmlInfo() throws UnsupportedEncodingException {
		
		 String chanl_cust_no = null; //客户渠道号
		 String signature = null; //数字签名
		 StringBuilder sb = new StringBuilder();  
	        sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
	        sb.append("<Message>");  
	        sb.append(" <header>");  
	        sb.append("     <action>readMeetingStatus</action>");  
	        sb.append("     <service>meeting</service>");  
	        sb.append("     <type>xml</type>");  
	        sb.append("     <userName>admin</userName>");  
	        sb.append("     <password>admin</password>");  
	        sb.append("     <siteName>box</siteName>");  
	        sb.append(" </header>");  
	        sb.append(" <body>");  
	        sb.append("     <confKey>43283344</confKey>");  
	        sb.append(" </body>");  
	        sb.append("</Message>");  

		// 如果xml里面存在汉字 一定要在参数传递之前 进行编码
		String urlEncoder = URLEncoder.encode(sb.toString(), "utf-8");
		//String result = transferData(("chanl_cust_no=" + chanl_cust_no + "&xml="
			//	+ urlEncoder + "&signature=" + signature), "utf-8",httpUrl);
		// System.out.println(data_digest);
		//System.out.println(result);
		//return result;
				
	 }
}
*/