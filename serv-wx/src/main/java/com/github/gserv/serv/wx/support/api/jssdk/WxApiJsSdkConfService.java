package com.github.gserv.serv.wx.support.api.jssdk;

import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.service.manager.WxService;

/**
 * 获取微信JsSdk配置属性
 * 
 * @author shiying
 *
 */
public interface WxApiJsSdkConfService extends WxService {
	
	/**
	 * 获取JsSdk配置属性
	 * @param pageurl 用于计算的URL地址
	 * @return
	 * @throws WxApiInvorkException
	 */
	public JsSdkConf generatorConf(String pageurl) throws WxApiInvorkException;
	
}
