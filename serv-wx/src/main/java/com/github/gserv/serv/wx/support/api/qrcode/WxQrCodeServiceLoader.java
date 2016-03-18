package com.github.gserv.serv.wx.support.api.qrcode;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.service.manager.WxServiceLoader;

public class WxQrCodeServiceLoader implements WxServiceLoader<WxQrCodeService> {
	
	private WxQrCodeService wxQrCodeService;

	@Override
	public synchronized WxQrCodeService load(WxServiceManager wxServiceManager) {
		if (wxQrCodeService != null) {
			return wxQrCodeService;
		}
		WxQrCodeService wxQrCodeService = new WxQrCodeService();
		wxQrCodeService.setWxServiceManager(wxServiceManager);
		return wxQrCodeService;
	}

}
