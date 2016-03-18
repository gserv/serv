package com.github.gserv.serv.wx.support.api.oauth;

public class WxEmojiFilter {
	
	/*
	*过滤存在的乱码 
	*/
	public static String dofilter(String str){
		String str_Result = "", str_OneStr = "";  
		for (int z = 0; z < str.length(); z++) {  
		   str_OneStr = str.substring(z, z + 1);  
		     if (str_OneStr.matches("[\u4e00-\u9fa5]+")||str_OneStr.matches("[\\x00-\\x7F]+")) 
		     {  
		       str_Result = str_Result + str_OneStr;    
		     }  
		}
		return str_Result;
	}
}
