package com.github.gserv.serv.wx.support.api.userinfo;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.service.manager.WxServiceLoader;

public class WxUserInfoServiceLoader implements WxServiceLoader<WxUserInfoService> {

	@Override
	public synchronized WxUserInfoService load(WxServiceManager wxServiceManager) {
		WxUserInfoService wxUserInfoService = new WxUserInfoService();
		wxUserInfoService.setWxServiceManager(wxServiceManager);
		return wxUserInfoService;
	}

}
