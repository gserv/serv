package com.github.gserv.serv.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	/**
	 * 用正则表达式查找子串
	 * @param content
	 * @param regex
	 * @param group
	 * @return
	 */
	public static String findGroup(String content, String regex, int group) {
		Matcher m = Pattern.compile(regex).matcher(content);
		if (!m.find()) {
			return null;
		}
		return m.group(group);
	}

}
