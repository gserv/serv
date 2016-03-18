package com.github.gserv.serv.web.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.gserv.serv.web.api.inter.ApiUserAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.github.gserv.serv.web.api.inter.ApiTokenService;

/**
 * Api Token 验证服务
 * 
 * @author shiying
 *
 */
public class ServWebApiTokenValidateController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(ServWebApiTokenValidateController.class);
	
	private ApiTokenService apiTokenService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (apiTokenService == null) {
			throw new RuntimeException("spring attributes [apiTokenService] is required");
		}
		//
		String token = request.getParameter("token");
		logger.debug("验证Token, token["+token+"]");
		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			if (apiTokenService.getToken(token) == null) {
				throw new ApiUserAuthenticationException("error_token", "validate faild");
			}
		} catch (ApiUserAuthenticationException e) {
			obj.put("err_code", e.getErr_code());
			obj.put("err_msg", e.getErr_msg());
			return new ModelAndView(new MappingJackson2JsonView(), obj);
		}
		obj.put("token", token);
		obj.put("expire", apiTokenService.getToken(token).getExpire());
		obj.put("err_code", "0000");
		obj.put("err_msg", "");
		return new ModelAndView(new MappingJackson2JsonView(), obj);
	}

	public ApiTokenService getApiTokenService() {
		return apiTokenService;
	}

	public void setApiTokenService(ApiTokenService apiTokenService) {
		this.apiTokenService = apiTokenService;
	}
	
	
	
	
	
	
}










