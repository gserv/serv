package com.github.gserv.serv.wx.service;

import java.util.Map;

import com.github.gserv.serv.wx.support.TemplateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认的模板处理器
 * 
 * @author shiying
 *
 */
public class DefaultTemplateHandler implements TemplateHandler {
	private static final Logger logger = LoggerFactory.getLogger(DefaultTemplateHandler.class);

	@Override
	public String handler(String template, Map<String, Object> data) {
		if (template == null) {
			return null;
		}
		logger.debug("template handler, template[{}], data[{}]", template, data);
		String str = template;
		for (String key : data.keySet()) {
			if (data.get(key) != null) {
				str = str.replaceAll("\\$\\{\\s*"+key+"\\s*\\}", data.get(key).toString());
			}
		}
		return str;
	}

}
