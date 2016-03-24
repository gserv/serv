package com.github.gserv.serv.wx.support.api.accesstoken;

import com.github.gserv.serv.wx.service.manager.WxServiceLoader;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxApiAccessTokenServiceLoader implements WxServiceLoader<WxApiAccessTokenService> {
	private static final Logger logger = LoggerFactory.getLogger(WxApiAccessTokenServiceLoader.class);

	/**
	 * 可选注入的WxApiAccessTokenService，当注入这个属性时，load方法自动返回该属性，忽略自身的逻辑
	 */
	private WxApiAccessTokenService wxApiAccessTokenService;
	
	/**
	 * 接口地址
	 */
	private String apiurl;

	@Override
	public synchronized WxApiAccessTokenService load(WxServiceManager wxServiceManager) {
		if (wxApiAccessTokenService != null) {
			try {
				logger.debug("发现优先wxApiAccessTokenService，尝试注入wxServiceManager");
				PropertyUtils.setProperty(wxApiAccessTokenService, "wxServiceManager", wxServiceManager);
			} catch (Exception e) {
				logger.warn("属性注入失败，可能wxServiceManager属性不存在：" + e.getMessage());
			}
			return wxApiAccessTokenService;
		}
		if (StringUtils.isNoneBlank(
				wxServiceManager.getProxyAccessTokenUrl(), 
				wxServiceManager.getProxyAccessTokenAccessId(), 
				wxServiceManager.getProxyAccessTokenAccessKey())) {
			WxApiAccessTokenProxyService wxApiAccessTokenProxyService = new WxApiAccessTokenProxyService();
			wxApiAccessTokenProxyService.setApiProxyUrl(wxServiceManager.getProxyAccessTokenUrl());
			wxApiAccessTokenProxyService.setAccessId(wxServiceManager.getProxyAccessTokenAccessId());
			wxApiAccessTokenProxyService.setAccessKey(wxServiceManager.getProxyAccessTokenAccessKey());
			wxApiAccessTokenProxyService.setWxServiceManager(wxServiceManager);
			return wxApiAccessTokenProxyService;
		} else {
			WxApiAccessTokenDefaultService wxApiAccessTokenDefaultService = new WxApiAccessTokenDefaultService();
			wxApiAccessTokenDefaultService.setAppid(wxServiceManager.getAppId());
			wxApiAccessTokenDefaultService.setSecret(wxServiceManager.getAppSecret());
			wxApiAccessTokenDefaultService.setWxServiceManager(wxServiceManager);
			if (apiurl != null) {
				wxApiAccessTokenDefaultService.setApiurl(apiurl);
			}
			return wxApiAccessTokenDefaultService;
		}
	}

	public String getApiurl() {
		return apiurl;
	}

	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
	}

	public WxApiAccessTokenService getWxApiAccessTokenService() {
		return wxApiAccessTokenService;
	}

	public void setWxApiAccessTokenService(WxApiAccessTokenService wxApiAccessTokenService) {
		this.wxApiAccessTokenService = wxApiAccessTokenService;
	}
}
