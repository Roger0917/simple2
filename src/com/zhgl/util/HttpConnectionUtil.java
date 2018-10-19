package com.zhgl.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
@Slf4j
public class HttpConnectionUtil {
	/**
	 * 
	 * @param content
	 * @param charset
	 * @param postUrl
	 * @return
	 * @throws IOException
	 */
	public static String transferData(String content, String postUrl) throws IOException {
		log.info("进入http请求方法");
	    HttpURLConnection connection = null;
	    BufferedReader reader = null;
	    StringBuilder sb = new StringBuilder();
	    try {
	      //byte[] postData = content.getBytes(charset);
	      //int postDataLength = postData.length;
	      // 配置连接
	      URL url = new URL(postUrl);

	      connection = (HttpURLConnection) url.openConnection();
	     /* if (connection instanceof HttpsURLConnection) {
	        SslContextUtils.createInstance().initHttpsConnect((HttpsURLConnection) connection);
	      }*/
	      // connection.setRequestProperty("Content-type", );
	      connection.setConnectTimeout(5000);
	      connection.setReadTimeout(20000);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
	      connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
	      connection.setRequestProperty("Charset", "UTF-8");
	      //connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
	      connection.setUseCaches(false);
	      // 发送请求
	      /*connection.getOutputStream().write(postData);
	      connection.getOutputStream().flush();
	      connection.getOutputStream().close();*/
	      //建立输入流，向指向的URL传入参数
	        DataOutputStream dos=new DataOutputStream(connection.getOutputStream());
	        dos.writeBytes(content);
	        dos.flush();
	        dos.close();
	      // 读取响应
	      reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

	      char[] buf = new char[1024];
	      int length = 0;
	      while ((length = reader.read(buf)) > 0) {
	        sb.append(buf, 0, length);
	      }
	    } finally {
	      if (connection != null) {
	        connection.disconnect();
	      }
	      if (reader != null) {
	        IOUtils.closeQuietly(reader);
	      }
	    }
	    String res = sb.toString();
	    return res;
	  }

	/**
	 * 
	 * @param actionUrl
	 * @param uploadFile
	 * @return
	 */
	/*@SuppressWarnings("finally")
	public static String uploadFile(String actionUrl, String uploadFile) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            // 统一资源
            URL url = new URL(actionUrl);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码连接参数
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置请求内容类型
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());
                String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
                ds.writeBytes(twoHyphens + boundary + end);
                ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + i + "\";filename=\"" + filename
                        + "\"" + end);
                ds.writeBytes(end);
                FileInputStream fStream = new FileInputStream(uploadFile);
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int length = -1;
                while ((length = fStream.read(buffer)) != -1) {
                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
                 //close streams 
                fStream.close();
            
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
             //close streams 
            ds.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                tempLine = null;
                resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (ds != null) {
                try {
                    ds.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return resultBuffer.toString();
        }
    }
	*/
	/**
	 * 
	 * @param urlPath
	 * @param downloadDir
	 * @return
	 */
	@SuppressWarnings("finally")
	public static File downloadFile(String urlPath, String downloadDir) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

            System.out.println("file length---->" + fileLength);

            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }
	}
}

    
