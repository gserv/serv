package serv.wx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import serv.wx.support.api.oauth.WxOauthContext;
import serv.wx.support.api.oauth.WxOauthContext.OauthScope;
import serv.wx.support.cache.Cache;

/**
 * 微信Oauth入口
 * 
 * @author shiying
 *
 */
public class WxOauthController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(WxOauthController.class);
	
	
	private WxApiAccessTokenService wxApiAccessTokenService;
	
	private String oauthCallbackUrl;
	
	private String oauthApiUrl = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
	private Cache cache;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 回调地址
		String redirectUrl = request.getParameter("redirectUrl");
		if (redirectUrl == null || redirectUrl.equals("")) {
			logger.debug("Illegal parameter : redirectUrl [{}]", redirectUrl);
			stringResult(response, "Illegal parameter : redirectUrl (caller)");
			return null;
		}
		redirectUrl = redirectUrl.startsWith("http") 
				? redirectUrl 
				: (request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+redirectUrl);
		// 是否为微信浏览器
		String ua = ((HttpServletRequest) request).getHeader("user-agent");
		if (ua == null || ua.toLowerCase().indexOf("micromessenger") == -1) {	// 不是微信浏览器
			logger.debug("not weixin brower");
			response.sendRedirect(
					redirectUrl + 
					(redirectUrl.indexOf("?") == -1 ? "?" : "&") + 
					"state=not_wx_browser");
			return null;
		}
		// scope
		String scope = request.getParameter("scope");
		OauthScope oauthScope = OauthScope.snsapi_base;
		try {
			oauthScope = OauthScope.valueOf(scope);
		} catch (Exception e) {}
		// 构建认证上下文
		WxOauthContext context = new WxOauthContext();
		context.setRedirectUrl(redirectUrl);
		context.setAppid(wxApiAccessTokenService.appId());
		context.setScope(oauthScope);
		// 缓存认证上下文
		String cacheKey = "oauthContext0"+UUID.randomUUID().toString().replace("-", "");
		cache.set(cacheKey, context);
		// 构建认证URL
		String oauthCallback = oauthCallbackUrl.startsWith("http") 
				? oauthCallbackUrl 
				: (request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+oauthCallbackUrl);
		String url = oauthApiUrl + "?appid=" + wxApiAccessTokenService.appId() 
				+ "&redirect_uri=" + URLEncoder.encode(oauthCallback, "UTF-8") 
				+ "&response_type=code"
				+ "&scope=" + (oauthScope == OauthScope.snsapi_userinfo_only ? OauthScope.snsapi_userinfo : OauthScope.snsapi_base)
				+ "&state=" + URLEncoder.encode(cacheKey, "UTF-8") 
				+ "#wechat_redirect";
		// 进入认证
		response.sendRedirect(url);
		return null;
	}
	
	
	private void stringResult(HttpServletResponse response, String message) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.append(message);
		writer.flush();
		writer.close();
	}


	public void setWxApiAccessTokenService(
			WxApiAccessTokenService wxApiAccessTokenService) {
		this.wxApiAccessTokenService = wxApiAccessTokenService;
	}


	public void setOauthCallbackUrl(String oauthCallbackUrl) {
		this.oauthCallbackUrl = oauthCallbackUrl;
	}


	public void setOauthApiUrl(String oauthApiUrl) {
		this.oauthApiUrl = oauthApiUrl;
	}


	public void setCache(Cache cache) {
		this.cache = cache;
	}
	

}








