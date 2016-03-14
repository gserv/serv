package serv.wx.support.api.serverIp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.HttpUtils;
import serv.commons.JsonUtils;
import serv.wx.service.manager.WxService;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenService;

/**
 * 动态二维码服务
 * 
 * @author shiying
 *
 */
public class WxServerIpService implements WxService {
	private static final Logger logger = LoggerFactory.getLogger(WxServerIpService.class);
	
	private WxServiceManager wxServiceManager;
	
	/**
	 * Server Ip 请求地址
	 */
	private String serverIpApiUrl = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
	
	/**
	 * 获得Server Ip
	 * @param scene_str
	 * @return
	 * @throws WxApiInvorkException
	 */
	public List<String> getServerIps() throws WxApiInvorkException {
		String url = serverIpApiUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
		try {
			String json = HttpUtils.get(url, new HashMap<String, String>());
			Map<String, Object> obj = JsonUtils.parseJsonMap(json);
			if (obj.get("ip_list") == null) {
				throw new WxApiInvorkException(json);
			}
			return (List<String>) obj.get("ip_list");
			
		} catch (IOException e) {
			throw new WxApiInvorkException("http request faild.", e);
		}
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	
	

}


