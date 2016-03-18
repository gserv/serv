package com.github.gserv.serv.web.shiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.github.gserv.serv.web.context.HttpRequestContext;

/**
 * Shiro退出控制器
 * 
 * @author shiying
 *
 */
public class ShiroLogoutController implements Controller {
	
	/**
	 * 退出后调用地址
	 */
	private String redirectUrl;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (redirectUrl == null) {
			throw new RuntimeException("redirectUrl is required");
		}
		SecurityUtils.getSubject().logout();
		response.sendRedirect(HttpRequestContext.getContext().getBasePath() + redirectUrl);
		return null;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	
	
}
