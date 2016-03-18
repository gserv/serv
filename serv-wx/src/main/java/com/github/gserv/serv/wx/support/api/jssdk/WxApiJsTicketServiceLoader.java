package com.github.gserv.serv.wx.support.api.jssdk;

import com.github.gserv.serv.wx.service.manager.WxServiceLoader;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;

/**
 * 获取微信JsSdk配置属性
 * 
 * @author shiying
 *
 */
public class WxApiJsTicketServiceLoader implements WxServiceLoader<WxApiJsTicketService> {

	@Override
	public WxApiJsTicketService load(WxServiceManager wxServiceManager) {
		WxApiJsTicketDefaultService wxApiJsTicketDefaultService = new WxApiJsTicketDefaultService();
		wxApiJsTicketDefaultService.setWxServiceManager(wxServiceManager);
		return wxApiJsTicketDefaultService;
	}
	
	
}
