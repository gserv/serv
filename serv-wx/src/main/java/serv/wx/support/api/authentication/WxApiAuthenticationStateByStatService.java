package serv.wx.support.api.authentication;

import java.util.Date;

import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.statistics.WxStatisticsService;

public class WxApiAuthenticationStateByStatService implements WxApiAuthenticationStateService {
	
	private WxServiceManager wxServiceManager;

	@Override
	public Boolean isAuthentication() throws WxApiInvorkException {
		WxStatisticsService wxStatisticsService = wxServiceManager.getWxService(WxStatisticsService.class);
		if (wxStatisticsService == null) {
			throw new RuntimeException("WxStatisticsService is required");
		}
		try {
			wxStatisticsService.getusercumulate(
					new Date(new Date().getTime() - 1000*60*60*24*2), 
					new Date(new Date().getTime() - 1000*60*60*24*1));
			return true;
		} catch (WxApiInvorkException e) {
			if (e.getErrcode() != null && e.getErrcode() == 48001) {
				return false;
			} 
			throw e;
		}
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}
	
	

}
