package serv.wx.support.api.jssdk;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.HttpUtils;
import serv.commons.JsonUtils;
import serv.commons.encry.HashUtils;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.WxApiInvorkException;

/**
 * JsSDK服务
 * 
 * @author shiying
 *
 */
public class WxApiJsTicketProxyService implements WxApiJsTicketService {
	private static final Logger logger = LoggerFactory.getLogger(WxApiJsTicketProxyService.class);
	
	/**
	 * 代理接口地址
	 */
	private String apiProxyUrl;
	
	/**
	 * 访问ID
	 */
	private String accessId;
	
	/**
	 * 访问密钥
	 */
	private String accessKey;
	
	/**
	 * 服务管理器
	 */
	private WxServiceManager wxServiceManager;

	@Override
	public String jsTicket(boolean refresh) throws WxApiInvorkException {
		Map<String, String> parmars = new HashMap<String, String>();
		parmars.put("accessid", accessId);
		parmars.put("sign", HashUtils.md5(accessId + accessKey).toLowerCase());
		parmars.put("reflush", ""+refresh);
		Map<String, Object> obj = null;
		try {
			String json = HttpUtils.get(apiProxyUrl, parmars);
			obj = JsonUtils.parseJsonMap(json);
			
		} catch (IOException e) {
			throw new WxApiInvorkException("http request faild", e); 
		}
		// 处理返回结果
		if (obj.get("js_ticket") == null) {
			throw new WxApiInvorkException("js_ticket request faild. res ["+JsonUtils.toJson(obj)+"]"); 
		}
		return (String) obj.get("js_ticket");
	}

	@Override
	public String jsTicket() throws WxApiInvorkException {
		return jsTicket(false);
	}

	public String getApiProxyUrl() {
		return apiProxyUrl;
	}

	public void setApiProxyUrl(String apiProxyUrl) {
		this.apiProxyUrl = apiProxyUrl;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	
	
	

}


