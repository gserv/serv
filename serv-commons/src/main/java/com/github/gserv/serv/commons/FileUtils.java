package com.github.gserv.serv.commons;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtils {
	
	/**
	 * 创建随机临时文件
	 * @param oriFileName
	 * @return
	 */
	public static File createTempFile(String oriFileName) {
		return new File(System.getProperty("java.io.tmpdir"), 
				new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_" + new Random().nextInt(99999) + "_" + oriFileName);
	}
	
	public static File getTomcatProjectPath() {
		URL url = FileUtils.class.getResource("/");
		try {
			File path = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
			return path.getParentFile().getParentFile();
			
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
