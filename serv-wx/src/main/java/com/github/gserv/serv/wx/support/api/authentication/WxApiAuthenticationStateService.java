package com.github.gserv.serv.wx.support.api.authentication;

import com.github.gserv.serv.wx.service.manager.WxService;
import com.github.gserv.serv.wx.support.WxApiInvorkException;

/**
 * 认证状态接口
 * 
 * @author shiying
 *
 */
public interface WxApiAuthenticationStateService extends WxService {
	
	/**
	 * 公众帐号是否认证
	 * @return
	 * @throws WxApiInvorkException
	 */
	public Boolean isAuthentication() throws WxApiInvorkException;
	
}
