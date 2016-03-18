package com.github.gserv.serv.web.api.inter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.encry.HashUtils;

/**
 * Api用户认证服务
 * 
 * @author shiying
 *
 */
public class ApiUserAuthService {
	private static final Logger logger = LoggerFactory.getLogger(ApiUserAuthService.class);
	
	/**
	 * 用户服务
	 */
	private ApiUserService apiUserService;
	
	/**
	 * 生成签名
	 * @param accessid
	 * @return
	 */
	public ApiUserAuth generatorSign(String accessId) {
		//
		ApiUserTokenSupport apiUserTokenSupport = apiUserService.getApiUserTokenSupportByAccessId(accessId);
		if (apiUserTokenSupport == null) {
			throw new ApiUserAuthenticationException(
					"error_accessid",
					"accessid not exist");
		}
		//
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = dateFormat.format(new Date());
		//
		String privateKey = apiUserTokenSupport.getPrivateKey();
		String g_sign = HashUtils.md5(accessId + timestamp + privateKey);
		//
		return new ApiUserAuth(accessId, timestamp, g_sign);
	}
	
	
	/**
	 * 认证并获取用户信息
	 * 
	 * @param accessid
	 * @param timestamp
	 * @param sign
	 * @return
	 * @throws ApiUserApiUserAuthenticationException
	 */
	public ApiUserTokenSupport authentication(ApiUserAuth auth) throws ApiUserAuthenticationException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date clientDate = null;
		try {
			clientDate = dateFormat.parse(auth.getTimestamp());
		} catch (Exception e) {
			logger.warn("时间戳解析失败[yyyyMMddHHmmss]：" + auth.getTimestamp());
			throw new ApiUserAuthenticationException(
					"error_timestamp_format",
					"Analysis of failure time stamp, please use the standard format: yyyyMMddHHmmss");
		}
		if (Math.abs(clientDate.getTime() - new Date().getTime()) > 1000 * 60 * 30) {
			throw new ApiUserAuthenticationException(
					"error_timestamp",
					"The server time difference is greater than the time allowed, please proofread the server time : " + dateFormat.format(new Date()));
		}
		ApiUserTokenSupport apiUserTokenSupport = apiUserService.getApiUserTokenSupportByAccessId(auth.getAccessId());
		if (apiUserTokenSupport == null) {
			throw new ApiUserAuthenticationException(
					"error_accessid",
					"accessid not exist");
		}
		String privatekey = apiUserTokenSupport.getPrivateKey();
		String g_sign = HashUtils.md5(auth.getAccessId() + auth.getTimestamp() + privatekey);
		if (!g_sign.equals(auth.getSign())) {
			throw new ApiUserAuthenticationException(
					"error_sign",
					"sign error");
		}
		return apiUserTokenSupport;
	}


	public ApiUserService getApiUserService() {
		return apiUserService;
	}


	public void setApiUserService(ApiUserService apiUserService) {
		this.apiUserService = apiUserService;
	}
	
	

	
	/**
	 * 认证实体
	 * 
	 * @author shiying
	 *
	 */
	public static class ApiUserAuth {
		String accessId;
		String timestamp;
		String sign;
		public ApiUserAuth(String accessId, String timestamp, String sign) {
			super();
			this.accessId = accessId;
			this.timestamp = timestamp;
			this.sign = sign;
		}
		public String getAccessId() {
			return accessId;
		}
		public void setAccessId(String accessId) {
			this.accessId = accessId;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
	}
	
	
}
