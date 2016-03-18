package com.github.gserv.serv.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gserv.serv.commons.encry.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API签名工具
 * 
 * @author shiying
 *
 */
public class ApiSignUtils {
	private static final Logger logger = LoggerFactory.getLogger(ApiSignUtils.class);
	
	
	public static void main(String[] arges) {
		System.out.println(rebuildParmars("http://www.baidu.com?", "sign", "aa"));
		System.out.println(rebuildParmars("http://www.baidu.com?a=asd&b=asf&c=da", "sign", "aa"));
		System.out.println(rebuildParmars("http://www.baidu.com?a=asd&b=asf&c=da&sign=aaa", "sign", "bb"));
	}
	
	
	public static String rebuildParmars(String url, String signName, String privateKey) {
		Map<String, Object> parmars = new HashMap<String, Object>();
		if (url.indexOf("?") != -1) {
			String urlParmar = url.substring(url.indexOf("?")+1, url.length());
			for (String kv : urlParmar.split("&")) {
				if (kv.trim().length() > 0 && kv.split("=").length == 2 && !kv.split("=")[0].equals(signName)) {
					parmars.put(kv.split("=")[0], kv.split("=")[1]);
				}
			}
			String sign = generatorSign(parmars, privateKey);
			//
			List<String> keys = Arrays.asList(parmars.keySet().toArray(new String[0]));
			Collections.sort(keys);
			StringBuffer buf = new StringBuffer();
			for (int i=0; i<keys.size(); i++) {
				buf.append(keys.get(i));
				buf.append("=");
				buf.append(parmars.get(keys.get(i)));
				buf.append("&");
			}
			buf.append(signName);
			buf.append("=");
			buf.append(sign);
			// 
			url = url.substring(0, url.indexOf("?")) + "?" + buf.toString();
		} else {
			url = url + "?sign=" + generatorSign(new HashMap<String, Object>(), privateKey);
		}
		return url;
	}
	
	
	/**
	 * 验证签名
	 * @param parmars
	 * @param signName
	 * @return
	 */
	public static boolean validateSign(Map<String, String[]> parmars, String signName, String privateKey) {
		if (parmars.get(signName) == null || parmars.get(signName).length <= 0) {
			return false;
		}
		String sign = parmars.get(signName)[0];
		//
		Map<String, Object> parmarsClone = JsonUtils.parseJsonMap(JsonUtils.toJson(parmars));
		parmarsClone.remove(signName);
		// 验证签名的有效性
		String _sign = generatorSign(parmarsClone, privateKey);
		if (!sign.equals(_sign)) {
			logger.warn("签名验证失败，传入[{}], 预期[{}]", sign, _sign);
		}
 		return sign.equals(_sign);
	}
	
	/**
	 * 生成签名的方法
	 * @param parmars
	 * @return
	 */
	public static String generatorSign(Map<String, Object> parmars, String privateKey) {
		// 变量重排序
		List<String> parmarNames = new ArrayList<String>();
		parmarNames.addAll(parmars.keySet());
		Collections.sort(parmarNames);
		// 按重排序的顺序生成 key1=value1&key2=value2 格式的参数串
		StringBuffer parmarsBuffer = new StringBuffer();
		for (int i=0; i<parmarNames.size(); i++) {
			String key = parmarNames.get(i);
			String value = (String) ((parmars.get(key) instanceof String[] || parmars.get(key) instanceof ArrayList) 
					? ((Arrays.asList(parmars.get(key)).get(0) instanceof List) 
							? (((List) Arrays.asList(parmars.get(key)).get(0)).get(0)) : (Arrays.asList(parmars.get(key)).get(0))).toString() 
					: ((parmars.get(key) instanceof List) 
							? (((List) parmars.get(key)).get(0)) : (parmars.get(key))).toString());
			//
			parmarsBuffer.append(key);
			parmarsBuffer.append("=");
			try {
				parmarsBuffer.append(URLEncoder.encode((String) value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			if (i < parmarNames.size()) {
				parmarsBuffer.append("&");
			}
		}
		parmarsBuffer.append(privateKey);
		// 计算hash
		String sign = HashUtils.md5(parmarsBuffer.toString());
		return sign;
	}

}
