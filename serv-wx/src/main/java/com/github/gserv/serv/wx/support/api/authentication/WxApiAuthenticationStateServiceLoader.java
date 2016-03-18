package com.github.gserv.serv.wx.support.api.authentication;

import com.github.gserv.serv.wx.service.manager.WxServiceLoader;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;

public class WxApiAuthenticationStateServiceLoader implements WxServiceLoader<WxApiAuthenticationStateService> {
	
	private WxServiceManager wxServiceManager;

	@Override
	public WxApiAuthenticationStateService load(WxServiceManager wxServiceManager) {
		WxApiAuthenticationStateByStatService service = new WxApiAuthenticationStateByStatService();
		service.setWxServiceManager(wxServiceManager);
		return service;
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}
	
}
