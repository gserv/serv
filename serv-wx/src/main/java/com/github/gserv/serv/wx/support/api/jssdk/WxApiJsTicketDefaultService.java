package com.github.gserv.serv.wx.support.api.jssdk;

import java.io.IOException;
import java.util.Map;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import com.github.gserv.serv.wx.support.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.HttpUtils;
import com.github.gserv.serv.commons.JsonUtils;

/**
 * JsSDK服务
 * 
 * @author shiying
 *
 */
public class WxApiJsTicketDefaultService implements WxApiJsTicketService {
	private static final Logger logger = LoggerFactory.getLogger(WxApiJsTicketDefaultService.class);
	
	/**
	 * Ticket获取API
	 */
	private String jsTicketApiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	/**
	 * 服务管理器
	 */
	private WxServiceManager wxServiceManager;

	@Override
	public String jsTicket(boolean refresh) throws WxApiInvorkException {
		String cacheKey = "jsticket_" + wxServiceManager.getAppId() + "";
		if (refresh) {
			wxServiceManager.getWxService(Cache.class).remove(cacheKey);
		}
		if (wxServiceManager.getWxService(Cache.class).get(cacheKey) != null) {
			return wxServiceManager.getWxService(Cache.class).get(cacheKey);
		}
		String url = jsTicketApiUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken() + "&type=jsapi";
		String json = null;
		try {
			json = HttpUtils.get(url, null);
		} catch (IOException e) {
			throw new WxApiInvorkException("invork http request faild.", e);
		}
		Map<String, Object> obj = JsonUtils.parseJsonMap(json);
		// 处理返回结果
		if (obj.get("ticket") == null) {
			throw new WxApiInvorkException("jsTicket request faild. res ["+json+"]"); 
		}
		wxServiceManager.getWxService(Cache.class).set(cacheKey, (String) obj.get("ticket"), (Integer) obj.get("expires_in"));
		logger.info("refresh js ticket success, ticket[{}]", obj.get("ticket"));
		return (String) obj.get("ticket");
	}

	@Override
	public String jsTicket() throws WxApiInvorkException {
		return jsTicket(false);
	}

	public String getJsTicketApiUrl() {
		return jsTicketApiUrl;
	}

	public void setJsTicketApiUrl(String jsTicketApiUrl) {
		this.jsTicketApiUrl = jsTicketApiUrl;
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	
	
	

}


