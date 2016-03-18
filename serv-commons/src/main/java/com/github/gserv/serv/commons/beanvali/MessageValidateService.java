package com.github.gserv.serv.commons.beanvali;

import java.util.Map;

/**
 * 消息验证服务
 * 
 * @author shiying
 *
 */
public interface MessageValidateService {
	
	/**
	 * 消息验证接口，提供通过一个表达式对消息的验证功能
	 * 
	 * @param regex
	 * @param object
	 * @return
	 */
	public boolean validate(String regex, Object object) throws MessageValidateException;
	
	/**
	 * 消息验证接口，提供通过一个表达式对消息的验证功能
	 * 
	 * @param regex
	 * @param object
	 * @param redata 验证完成后，表达式返回的数据
	 * @return
	 */
	public boolean validate(String regex, Object object, Map<String, Object> redata) throws MessageValidateException;
	
}
