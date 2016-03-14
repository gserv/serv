package serv.wx.support.api.oauth;

import java.io.Serializable;

/**
 * Oauth 认证上下文
 * 
 * @author shiying
 *
 */
public class WxOauthContext implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4629629238627159742L;

	/**
	 * AppId
	 */
	private String appid;
	
	/**
	 * 回调地址
	 */
	private String redirectUrl;
	
	/**
	 * 应用授权作用域
	 */
	private OauthScope scope;
	
	
	
	
	public WxOauthContext(String appid, String redirectUrl, OauthScope scope) {
		super();
		this.appid = appid;
		this.redirectUrl = redirectUrl;
		this.scope = scope;
	}

	public WxOauthContext() {
		super();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public OauthScope getScope() {
		return scope;
	}


	public void setScope(OauthScope scope) {
		this.scope = scope;
	}




	/**
	 * Oauth认证作用域类型
	 * 
	 * @author shiying
	 *
	 */
	public static enum OauthScope {
		snsapi_base, 		// 获取Openid
		snsapi_userinfo, 	// 试授权，先使用snsapi_base，获取openid后使用snsapi_userinfo
		snsapi_userinfo_only		// 仅使用snsapi_userinfo认证
	}
	
}
