package com.github.gserv.serv.wx.support.api.accesstoken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.HttpUtils;
import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.commons.inter.RetryTimeLimitUtils;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.cache.Cache;

/**
 * 默认的AccessToken获取方法，直接调用微信API
 * 
 * @author shiying
 *
 */
public class WxApiAccessTokenDefaultService implements WxApiAccessTokenService {
	private static final Logger logger = LoggerFactory.getLogger(WxApiAccessTokenDefaultService.class);
	
	/**
	 * 微信AppID
	 */
	private String appid;
	
	/**
	 * 微信Secret
	 */
	private String secret;
	
	/**
	 * 服务管理器
	 */
	private WxServiceManager wxServiceManager;
	
	/**
	 * 接口地址
	 */
	private String apiurl = "https://api.weixin.qq.com/cgi-bin/token";
	
	/**
	 * 重试次数检查工具
	 * 每10分钟刷新不能超过10次
	 */
	private RetryTimeLimitUtils retryTimeLimitUtils = new RetryTimeLimitUtils(10, 10, TimeUnit.MINUTES);

	@Override
	public String accessToken() throws WxApiInvorkException {
		return accessToken(false);
	}

	@Override
	public String appId() throws WxApiInvorkException {
		return appid;
	}

	@Override
	public String accessToken(boolean refresh) throws WxApiInvorkException {
		String cacheKey = "accessToken_" + appid;
		if (refresh) {
			if (retryTimeLimitUtils.check(cacheKey)) {
				retryTimeLimitUtils.logRequest(cacheKey);
				wxServiceManager.getWxService(Cache.class).remove(cacheKey);
			} else {
				logger.warn("AccessToken刷新过于频繁，拒绝本地刷新请求");
				refresh = false;
			}
		}
		if (wxServiceManager.getWxService(Cache.class).get(cacheKey) != null) {
			return wxServiceManager.getWxService(Cache.class).get(cacheKey);
		}
		if (appid == null || secret == null) {
			throw new WxApiInvorkException("appid or secret is null"); 
		}
		Map<String, String> data = new HashMap<String, String>();
		data.put("grant_type", "client_credential");
		data.put("appid", appid);
		data.put("secret", secret);
		//
		Map<String, Object> obj = null;
		try {
			String json = HttpUtils.post(apiurl, data);
			obj = JsonUtils.parseJsonMap(json);
			
		} catch (IOException e) {
			throw new WxApiInvorkException("http request faild, url["+apiurl+"], data["+JsonUtils.toJson(data)+"]", e); 
		}
		// 处理返回结果
		if (obj.get("access_token") == null) {
			throw new WxApiInvorkException("access_token request faild. res ["+JsonUtils.toJson(obj)+"]"); 
		}
		wxServiceManager.getWxService(Cache.class).set(cacheKey, (String) obj.get("access_token"), (Integer) obj.get("expires_in"));
		logger.info("refresh access token success, accessToken[{}]", obj.get("access_token"));
		return (String) obj.get("access_token");
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAppid() {
		return appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}
	
	

}
