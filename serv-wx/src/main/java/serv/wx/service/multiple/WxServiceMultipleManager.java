package serv.wx.service.multiple;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.wx.conf.WxConf;
import serv.wx.service.manager.SimpleWxServiceManager;
import serv.wx.service.manager.WxServiceManager;

/**
 * 多微信接入管理器
 * 
 * @author shiying
 *
 */
public class WxServiceMultipleManager {
	private static final Logger logger = LoggerFactory.getLogger(WxServiceMultipleManager.class);
	
	/**
	 * 微信配置支持
	 */
	private WxServiceMultipleSupport wxServiceMultipleSupport;
	
	/**
	 * 服务管理器缓存
	 */
	private Map<String, WxServiceManager> wxServiceManagerCache = new HashMap<String, WxServiceManager>();
	
	/**
	 * 获取微信服务管理器
	 * @return
	 */
	public WxServiceManager getWxServiceManager(String appId) {
		if (wxServiceManagerCache.get(appId) != null) {
			return wxServiceManagerCache.get(appId);
		}
		WxConf wxConf = wxServiceMultipleSupport.getWxConfByAppid(appId);
		if (wxConf == null) {
			return null;
		}
		SimpleWxServiceManager wxServiceManager = new SimpleWxServiceManager(wxConf);
		wxServiceManager.init();
		wxServiceManagerCache.put(appId, wxServiceManager);
		logger.debug("init server manager, appId[{}], manager[{}]", appId, wxServiceManager);
		return wxServiceManager;
	}
	
	/**
	 * 获取微信配置
	 * @param appId
	 * @return
	 */
	public WxConf getWxConfByWxAppId(String appId) {
		return wxServiceMultipleSupport.getWxConfByAppid(appId);
	}

	public void setWxServiceMultipleSupport(
			WxServiceMultipleSupport wxServiceMultipleSupport) {
		this.wxServiceMultipleSupport = wxServiceMultipleSupport;
	}
	
	
	

}
