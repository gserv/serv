package serv.wx.support.api.oauth;

import org.apache.commons.lang3.StringUtils;

import serv.commons.JsonUtils;
import serv.wx.service.manager.WxServiceLoader;
import serv.wx.service.manager.WxServiceManager;

public class WxOauthServiceLoader implements WxServiceLoader<WxOauthService> {
	
	/**
	 * 接口地址
	 */
	private String apiurl;

	@Override
	public synchronized WxOauthService load(WxServiceManager wxServiceManager) {
		if (!StringUtils.isNoneBlank(
				wxServiceManager.getAppId(), 
				wxServiceManager.getAppSecret())) {
			throw new RuntimeException("oauth service is required weixin secret");
		}
		WxOauthService service = new WxOauthService();
		service.setWxServiceManager(wxServiceManager);
		return service;
	}

	public String getApiurl() {
		return apiurl;
	}

	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
	}

	
}
