package com.github.gserv.serv.wx.support.api.userinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.HttpUtils;
import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.wx.service.manager.WxService;

/**
 * 微信用户信息支持
 * 
 * @author shiying
 *
 */
public class WxUserInfoService implements WxService {
	
	private static final Logger logger = LoggerFactory.getLogger(WxUserInfoService.class);
	
	private WxServiceManager wxServiceManager;
	
	/**
	 * 获取用户信息URL
	 */
	private String userInfoApiOnceUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	/**
	 * 批量获取用户信息URL
	 */
	private String userInfoApiBatchUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
	
	/**
	 * 获取用户信息
	 * @param openid
	 * @return
	 * @throws WxApiInvorkException
	 */
	public WeixinUser getUserInfo(String openid) throws WxApiInvorkException {
		String json = null;
		try {
			String url = userInfoApiOnceUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken() + "&openid=" + openid + "&lang=zh_CN";
			json = HttpUtils.get(url, null);
		} catch (IOException e) {
			throw new WxApiInvorkException("fire http request faild.", e);
		}
		WeixinUser weixinUser = JsonUtils.parseJson(json, WeixinUser.class);
		return weixinUser;
	}
	
	/**
	 * 获取用户信息
	 * @param openid
	 * @return
	 * @throws WxApiInvorkException
	 */
	public List<WeixinUser> getUserInfo(Set<String> openid) throws WxApiInvorkException {
		String json = null;
		try {
			String url = userInfoApiBatchUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
			// 构造请求对象
			List<Map<String, Object>> openids = new ArrayList<Map<String, Object>>();
			for (String id : openid) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("openid", id);
				item.put("lang", "zh-CN");
				openids.add(item);
			}
			Map<String, Object> req = new HashMap<String, Object>();
			req.put("user_list", openids);
			// 发送请求
			logger.debug("build request userinfo object complate. body [{}]", JsonUtils.toJson(req));
			json = HttpUtils.post(url, JsonUtils.toJson(req));
			logger.debug("request userinfo complate. body [{}]", JsonUtils.toJson(req));
		} catch (IOException e) {
			throw new WxApiInvorkException("fire http request faild.", e);
		}
		Map<String, Object> res = JsonUtils.parseJsonMap(json);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) res.get("user_info_list");
		List<WeixinUser> users = new ArrayList<WeixinUser>();
		for (Map<String, Object> map : list) {
			users.add(JsonUtils.parseJson(JsonUtils.toJson(map), WeixinUser.class));
		}
		return users;
	}
	

	public void setUserInfoApiOnceUrl(String userInfoApiOnceUrl) {
		this.userInfoApiOnceUrl = userInfoApiOnceUrl;
	}

	public void setUserInfoApiBatchUrl(String userInfoApiBatchUrl) {
		this.userInfoApiBatchUrl = userInfoApiBatchUrl;
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	
	
}
