package serv.wx.support.api.service;

import serv.wx.service.manager.WxServiceLoader;
import serv.wx.service.manager.WxServiceManager;

public class WxServiceMessagePushServiceLoader implements WxServiceLoader<WxServiceMessagePushService> {

	@Override
	public synchronized WxServiceMessagePushService load(WxServiceManager wxServiceManager) {
		WxServiceMessagePushService wxServiceMessagePushService = new WxServiceMessagePushService();
		wxServiceMessagePushService.setWxServiceManager(wxServiceManager);
		return wxServiceMessagePushService;
	}
	
	
	

}
