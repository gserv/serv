package com.github.gserv.serv.wx.support.api.accesstoken;

import com.github.gserv.serv.wx.service.manager.WxService;
import com.github.gserv.serv.wx.support.WxApiInvorkException;

/**
 * 微信API接口，获取AccessToken
 * 
 * @author shiying
 *
 */
public interface WxApiAccessTokenService extends WxService {
	
	/**
	 * 获取AccessToken
	 * @param refresh 强制刷新（不建议）
	 * @return
	 * @throws WxApiInvorkException
	 */
	public String accessToken(boolean refresh) throws WxApiInvorkException;
	
	/**
	 * 获取AccessToken
	 * @return
	 * @throws WxApiInvorkException
	 */
	public String accessToken() throws WxApiInvorkException;
	
	/**
	 * 获取AppId
	 * @return
	 * @throws WxApiInvorkException
	 */
	public String appId() throws WxApiInvorkException;
	
}
