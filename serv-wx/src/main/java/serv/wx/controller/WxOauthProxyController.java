package serv.wx.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 微信Oauth入口
 * 
 * @author shiying
 *
 */
public class WxOauthProxyController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(WxOauthProxyController.class);
	
	private String wxOauthProxyApiUrl;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String scope = request.getParameter("scope");
		String redirectUrl = request.getParameter("redirectUrl");
		if (!"snsapi_userinfo".equals(scope) && !"snsapi_base".equals(scope)) {
			scope = "snsapi_base";
		}
		redirectUrl = redirectUrl.startsWith("http") 
				? redirectUrl 
				: (request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+redirectUrl);
		String url = wxOauthProxyApiUrl 
				+ (wxOauthProxyApiUrl.indexOf("?") == -1 ? "?" : "&")
				+ "scope=" + scope 
				+ "&redirectUrl=" + URLEncoder.encode(redirectUrl, "UTF-8") ;
		logger.warn("invork weixin oauth proxy, use url [{}]", url);
		response.sendRedirect(url);
		return null;
	}

	public String getWxOauthProxyApiUrl() {
		return wxOauthProxyApiUrl;
	}

	public void setWxOauthProxyApiUrl(String wxOauthProxyApiUrl) {
		this.wxOauthProxyApiUrl = wxOauthProxyApiUrl;
	}
	
	
	
	

}








