package serv.wx.support.api.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import serv.commons.HttpUtils;
import serv.commons.JsonUtils;
import serv.wx.service.manager.WxService;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.userinfo.WeixinUser;

/**
 * 微信API接口，Oauth 支持
 * 
 * @author shiying
 *
 */
public class WxOauthService implements WxService {

	
	/**
	 * Oauth access_token 获取接口
	 */
	private String oauthAccessTokenApiUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/**
	 * 用户信息获取接口
	 */
	private String oauthUserInfoApiUrl = "https://api.weixin.qq.com/sns/userinfo";
	
	
	private WxServiceManager wxServiceManager;

	
	/**
	 * 获得Oauth请求地址
	 * @param realUrl
	 * @param state
	 * @param scope
	 * @param appid
	 * @param secret
	 * @return
	 */
	public String getOauthRequestUrl(
			String realUrl, String state, String scope) {
		try {
			return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wxServiceManager.getAppId()+"&redirect_uri="
							+URLEncoder.encode(realUrl, "UTF-8")+"&response_type=code&scope="+scope+"&state="+state+"#wechat_redirect";
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	
	/**
	 * 获取用户信息
	 * @param token
	 * @return
	 * @throws WxApiInvorkException
	 */
	public WeixinUser getOauthUser(WxOauthAccessToken token) throws WxApiInvorkException {
		String url = oauthUserInfoApiUrl + "?access_token=" + token.getAccess_token() + "&openid=" + token.getOpenid() + "&lang=zh_CN";
		String json = null;
		try {
			json = HttpUtils.get(url, null);
		} catch (IOException e) {
			throw new WxApiInvorkException("weixin api http request faild.", e);
		}
		WeixinUser weixinUser = JsonUtils.parseJson(json, WeixinUser.class);
		if (weixinUser == null) {
			throw new WxApiInvorkException("weixin api invork faild.");
		}
		return weixinUser;
	}
	
	
	/**
	 * 通过Oauth带回的Code获取认证需要的AccessToken
	 * @param code
	 * @return
	 * @throws WxApiInvorkException 
	 */
	public WxOauthAccessToken getOauthAccessToken(String code) throws WxApiInvorkException {
		String url = oauthAccessTokenApiUrl 
				+ "?appid=" + wxServiceManager.getAppId()
				+ "&secret=" + wxServiceManager.getAppSecret()
				+ "&code=" + code 
				+ "&grant_type=authorization_code";
		String json = null;
		try {
			json = HttpUtils.get(url, null);
		} catch (IOException e) {
			throw new WxApiInvorkException("invork weixin api faild.", e);
		}
		WxOauthAccessToken token = JsonUtils.parseJson(json, WxOauthAccessToken.class);
		if (token == null) {
			throw new WxApiInvorkException("invork weixin api faild, body["+json+"].");
		}
		return token;
	}


	public String getOauthAccessTokenApiUrl() {
		return oauthAccessTokenApiUrl;
	}


	public void setOauthAccessTokenApiUrl(String oauthAccessTokenApiUrl) {
		this.oauthAccessTokenApiUrl = oauthAccessTokenApiUrl;
	}


	public String getOauthUserInfoApiUrl() {
		return oauthUserInfoApiUrl;
	}


	public void setOauthUserInfoApiUrl(String oauthUserInfoApiUrl) {
		this.oauthUserInfoApiUrl = oauthUserInfoApiUrl;
	}


	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}


	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}
	
	
	
	
	
}
