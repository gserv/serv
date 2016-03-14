package serv.wx.service.multiple;

import serv.wx.conf.WxConf;

/**
 * 多微信接入支持
 * 
 * @author shiying
 *
 */
public interface WxServiceMultipleSupport {
	
	/**
	 * 通过微信AppId加载微信配置
	 * @param appId
	 * @return
	 */
	public WxConf getWxConfByAppid(String appId);

}
