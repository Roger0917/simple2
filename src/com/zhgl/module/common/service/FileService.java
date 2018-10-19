/*package com.zhgl.module.common.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.zhgl.module.common.service.TranscationService;

@Service
public class FileService {

	@Autowired
	private TranscationService transcationService;
	String upUrl = "http://ip:port/interlink/UploadFile";
	String downUrl = "http://ip:port/interlink/DownloadFile";
	private String chanl_cust_no; //电子银行合约编号
	
	*//**
	 * 文件上传
	 * @param chanl_cust_no
	 * @param filename
	 * @param signature
	 * @param file
	 * @param dirflag
	 * @return
	 *//*
	public String fileUpload(String chanl_cust_no,String filename,String signature,File file,String dirflag){
		String result = transcationService.post("chanl_cust_no=" + chanl_cust_no + "&filename="
				+ filename + "&signature=" + signature +"&data="+ "&dirflag="+dirflag, upUrl);
		
		return result;
	}
	
	*//**
	 * 文件下载
	 * @param chanl_cust_no
	 * @param filename
	 * @param signature
	 * @param file
	 * @param dirflag
	 * @return
	 *//*
	public String fileDownload(String chanl_cust_no,String filename,String signature,File file,int dirflag){
		String result = transcationService.post("chanl_cust_no=" + chanl_cust_no + "&filename="
				+ filename + "&signature=" + signature, downUrl);
		
		return result;
	}
}
*/