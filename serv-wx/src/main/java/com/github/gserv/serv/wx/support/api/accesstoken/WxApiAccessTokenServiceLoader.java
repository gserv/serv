package com.github.gserv.serv.wx.support.api.accesstoken;

import com.github.gserv.serv.wx.service.manager.WxServiceLoader;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import org.apache.commons.lang3.StringUtils;

public class WxApiAccessTokenServiceLoader implements WxServiceLoader<WxApiAccessTokenService> {
	
	/**
	 * 接口地址
	 */
	private String apiurl;

	@Override
	public synchronized WxApiAccessTokenService load(WxServiceManager wxServiceManager) {
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

	
}
