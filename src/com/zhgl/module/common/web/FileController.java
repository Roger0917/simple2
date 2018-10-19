//package com.zhgl.module.common.web;
//
//import java.io.File;
//
//import org.apache.commons.fileupload.disk.DiskFileItem;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import com.zhgl.module.common.service.FileService;
//import com.zhgl.util.Des3Util;
//import com.zhgl.util.DigitalSignatureUtil;
//
//@Controller
//@RequestMapping("/file")
//public class FileController {
//	String key = "123456";
//	@Autowired
//	private FileService fileService;
//	@RequestMapping("/up")
//	public void fileUp(String chanl_cust_no,@RequestParam("file") CommonsMultipartFile file) throws Exception{
//		String filename = file.getOriginalFilename();
//		//对文件名用3des加密
//		String encryfilename = Des3Util.encode3Des(key, filename);
//		//对文件名用数字签名
//		String signfilename = DigitalSignatureUtil.getMd5Sign(filename,DigitalSignatureUtil.getKeyPair().getPrivate());
//		//对文件内容进行3des加密
//		
//		//对dirflag进行加密
//		String firflag = Des3Util.encode3Des(key, 1+"");
//		fileService.fileUpload(chanl_cust_no, encryfilename, signfilename, file, 1);
//	}
//	
//	@RequestMapping("/down")
//	
//	
//	public File getFile(CommonsMultipartFile cmfFile) {
//        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) cmfFile;
//        DiskFileItem diskFileItem = (DiskFileItem) commonsMultipartFile.getFileItem();
//        File file = diskFileItem.getStoreLocation();
//        return file;
//    }
//	
//	
//	
//	
//}
