package serv.wx.support.api.accesstoken;

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
 * 用代理方法获取AccessToken
 * 
 * @author shiying
 *
 */
public class WxApiAccessTokenProxyService implements WxApiAccessTokenService {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(WxApiAccessTokenProxyService.class);
	
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
	
	/**
	 * 请求API
	 * 
	 * @param refresh
	 * @return
	 * @throws WxApiInvorkException
	 */
	private Map<String, Object> accessTokenApiInvork(boolean refresh) throws WxApiInvorkException {
		Map<String, String> parmars = new HashMap<String, String>();
		parmars.put("accessid", accessId);
		parmars.put("sign", HashUtils.md5(accessId + accessKey).toLowerCase());
		parmars.put("reflush", ""+refresh);
		Map<String, Object> obj = null;
		try {
			String json = HttpUtils.get(apiProxyUrl, parmars);
			obj = JsonUtils.parseJsonMap(json);
			
		} catch (IOException e) {
			throw new WxApiInvorkException("http request faild, url["+apiProxyUrl+"], data["+JsonUtils.toJson(parmars)+"]", e); 
		}
		// 处理返回结果
		if (obj.get("access_token") == null) {
			throw new WxApiInvorkException("access_token request faild. res ["+JsonUtils.toJson(obj)+"]"); 
		}
		return obj;
	}

	@Override
	public String accessToken(boolean refresh) throws WxApiInvorkException {
		return (String) accessTokenApiInvork(refresh).get("access_token");
	}

	@Override
	public String accessToken() throws WxApiInvorkException {
		return accessToken(false);
	}

	@Override
	public String appId() throws WxApiInvorkException {
		return (String) accessTokenApiInvork(false).get("appId");
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
