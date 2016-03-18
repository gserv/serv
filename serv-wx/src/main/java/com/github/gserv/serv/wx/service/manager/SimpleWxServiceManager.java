package com.github.gserv.serv.wx.service.manager;

import java.lang.reflect.InvocationTargetException;

import com.github.gserv.serv.wx.conf.WxConf;
import org.apache.commons.beanutils.BeanUtils;

public class SimpleWxServiceManager extends WxServiceManager {

	
	private String appId;
	
	private String appSecret;
	
	private String url;
	
	private String token;
	
	private String encodingAESKey;
	
	private String proxyAccessTokenUrl;
	
	private String proxyAccessTokenAccessId;
	
	private String proxyAccessTokenAccessKey;
	
	
	public SimpleWxServiceManager() {}
	
	public SimpleWxServiceManager(WxConf wxConf) {
		try {
			BeanUtils.copyProperties(this, wxConf);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}

	public String getProxyAccessTokenUrl() {
		return proxyAccessTokenUrl;
	}

	public void setProxyAccessTokenUrl(String proxyAccessTokenUrl) {
		this.proxyAccessTokenUrl = proxyAccessTokenUrl;
	}

	public String getProxyAccessTokenAccessId() {
		return proxyAccessTokenAccessId;
	}

	public void setProxyAccessTokenAccessId(String proxyAccessTokenAccessId) {
		this.proxyAccessTokenAccessId = proxyAccessTokenAccessId;
	}

	public String getProxyAccessTokenAccessKey() {
		return proxyAccessTokenAccessKey;
	}

	public void setProxyAccessTokenAccessKey(String proxyAccessTokenAccessKey) {
		this.proxyAccessTokenAccessKey = proxyAccessTokenAccessKey;
	}


}
