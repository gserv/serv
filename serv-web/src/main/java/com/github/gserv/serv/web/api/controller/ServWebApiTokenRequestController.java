package com.github.gserv.serv.web.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.gserv.serv.web.api.inter.ApiUserAuthService;
import com.github.gserv.serv.web.api.inter.ApiUserAuthenticationException;
import com.github.gserv.serv.web.api.inter.ApiUserTokenSupport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.web.api.inter.ApiTokenService;

/**
 * 请求ApiToken的控制器
 * 
 * @author shiying
 *
 */
public class ServWebApiTokenRequestController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(ServWebApiTokenRequestController.class);
	
	private ApiUserAuthService apiUserAuthService;
	
	private ApiTokenService apiTokenService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (apiTokenService == null || apiTokenService == null) {
			throw new RuntimeException("spring attributes [apiUserAuthService, apiTokenService] is required");
		}
		String accessid = request.getParameter("accessid");
		String timestamp = request.getParameter("timestamp");
		String sign = request.getParameter("sign");
		List parmar = JsonUtils.parseJson(request.getParameter("parmar"), List.class);
		logger.debug("用户请求Token, accessid["+accessid+"], timestamp["+timestamp+"], sign["+sign+"], parmar["+parmar+"]");
		//
		Map<String, Object> obj = new HashMap<String, Object>();
		if (!StringUtils.isNoneBlank(accessid, timestamp, sign)) {
			obj.put("err_code", "error_parmar");
			obj.put("err_msg", "parmars [accessid, timestamp, sign] is required");
			return new ModelAndView(new MappingJackson2JsonView(), obj);
		}
		//
		ApiUserTokenSupport apiUserTokenSupport = null;
		try {
			apiUserTokenSupport = apiUserAuthService.authentication(new ApiUserAuthService.ApiUserAuth(accessid, timestamp, sign));
		} catch (ApiUserAuthenticationException e) {
			logger.warn("AuthenticationException", e);
			obj.put("err_code", e.getErr_code());
			obj.put("err_msg", e.getErr_msg());
			return new ModelAndView(new MappingJackson2JsonView(), obj);
		}
		// 计算token
		String token = apiTokenService.generatorToken(accessid, parmar.toArray());
		// 构造返回消息
		obj.put("token", token);
		obj.put("expire", apiUserTokenSupport.getTokenExpire());
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

	public ApiUserAuthService getApiUserAuthService() {
		return apiUserAuthService;
	}

	public void setApiUserAuthService(ApiUserAuthService apiUserAuthService) {
		this.apiUserAuthService = apiUserAuthService;
	}
	
	
	
	

}
