package com.github.gserv.serv.wx.support.api.service;

import com.github.gserv.serv.wx.service.manager.WxServiceLoader;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;

public class WxServiceMessagePushServiceLoader implements WxServiceLoader<WxServiceMessagePushService> {

	@Override
	public synchronized WxServiceMessagePushService load(WxServiceManager wxServiceManager) {
		WxServiceMessagePushService wxServiceMessagePushService = new WxServiceMessagePushService();
		wxServiceMessagePushService.setWxServiceManager(wxServiceManager);
		return wxServiceMessagePushService;
	}
	
	
	

}
