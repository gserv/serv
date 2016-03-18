package com.github.gserv.serv.wx.support.api.jssdk;

import com.github.gserv.serv.wx.service.manager.WxServiceLoader;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;

/**
 * 获取微信JsSdk配置属性
 * 
 * @author shiying
 *
 */
public class WxApiJsSdkConfServiceLoader implements WxServiceLoader<WxApiJsSdkConfService> {

	@Override
	public WxApiJsSdkConfService load(WxServiceManager wxServiceManager) {
		WxJsSdkConfDefaultService wxJsSdkConfDefaultService = new WxJsSdkConfDefaultService();
		wxJsSdkConfDefaultService.setWxServiceManager(wxServiceManager);
		return wxJsSdkConfDefaultService;
	}
	
	
}
