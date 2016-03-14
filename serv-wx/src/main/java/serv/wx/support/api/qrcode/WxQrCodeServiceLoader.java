package serv.wx.support.api.qrcode;

import serv.wx.service.manager.WxServiceLoader;
import serv.wx.service.manager.WxServiceManager;

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
