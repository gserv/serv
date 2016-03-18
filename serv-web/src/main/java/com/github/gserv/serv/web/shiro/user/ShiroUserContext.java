package com.github.gserv.serv.web.shiro.user;

import org.apache.shiro.SecurityUtils;

import com.github.gserv.serv.web.context.HttpRequestContext;

/**
 * Shiro用户工具
 * 
 * @author shiying
 *
 */
public class ShiroUserContext {
	
	/**
	 * 存储的SessionKey
	 * @return
	 */
	public static String getShiroUserSessionKey() {
		return "SERV_SHIRO_USERINFO";
	}
	
	/**
	 * 取出的用户信息
	 * @return
	 */
	public static ShiroUser getShiroUser() {
		return (ShiroUser) HttpRequestContext.getContext().getRequest().getSession().getAttribute(getShiroUserSessionKey());
	}
	
	/**
	 * 取出的用户信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ShiroUser> T getShiroUser(Class<T> t) {
		return (T) HttpRequestContext.getContext().getRequest().getSession().getAttribute(getShiroUserSessionKey());
	}
	
	/**
	 * 获得登录用户名
	 * @return
	 */
	public static String getLoginUsername() {
		return SecurityUtils.getSubject().getPrincipal().toString();
	}
	
	
	
	
	
	
	
	
}














