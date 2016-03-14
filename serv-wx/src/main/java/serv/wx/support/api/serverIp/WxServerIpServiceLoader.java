package serv.wx.support.api.serverIp;

import serv.wx.service.manager.WxServiceLoader;
import serv.wx.service.manager.WxServiceManager;

public class WxServerIpServiceLoader implements WxServiceLoader<WxServerIpService> {
	
	private WxServerIpService wxQrCodeService;

	@Override
	public synchronized WxServerIpService load(WxServiceManager wxServiceManager) {
		if (wxQrCodeService != null) {
			return wxQrCodeService;
		}
		WxServerIpService wxQrCodeService = new WxServerIpService();
		wxQrCodeService.setWxServiceManager(wxServiceManager);
		return wxQrCodeService;
	}

}
