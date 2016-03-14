package serv.wx.support.api.userinfo;

import serv.wx.service.manager.WxServiceLoader;
import serv.wx.service.manager.WxServiceManager;

public class WxUserInfoServiceLoader implements WxServiceLoader<WxUserInfoService> {

	@Override
	public synchronized WxUserInfoService load(WxServiceManager wxServiceManager) {
		WxUserInfoService wxUserInfoService = new WxUserInfoService();
		wxUserInfoService.setWxServiceManager(wxServiceManager);
		return wxUserInfoService;
	}

}
