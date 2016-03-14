package serv.wx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import serv.wx.support.api.oauth.WxOauthContext.OauthScope;

/**
 * 微信Oauth演示
 * 
 * @author shiying
 *
 */
public class WxOauthDemoController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(WxOauthDemoController.class);
	
	private String wxOauthProxyApiUrl;
	
	private String wxOauthCallbackUrl;
	
	/**
	 * 签名私钥
	 */
	private String signPrivateKey = "default";
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String state = request.getParameter("state");
		if (state == null || state.equals("")) {
			String testScope = request.getParameter("testScope");
			OauthScope scope = OauthScope.snsapi_base;
			try {
				scope = OauthScope.valueOf(testScope);
			} catch (Exception e) {}
			String url = wxOauthProxyApiUrl.startsWith("http") ? wxOauthProxyApiUrl 
					: (request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+wxOauthProxyApiUrl);
			url = url + 
					"?redirectUrl=" + URLEncoder.encode(wxOauthCallbackUrl, "UTF-8") + 
					"&scope=" + scope.toString();
			response.sendRedirect(url);
			return null;
			
		} else {
			StringBuffer buf = new StringBuffer();
			buf.append("<div><ul>");
			Enumeration<String> e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String key = e.nextElement();
				buf.append("<li>");
				buf.append("<strong>" + key + "</strong>");
				buf.append(" : ");
				buf.append(request.getParameter(key));
				buf.append("</li>");
			}
			buf.append("</ul></div>");
			stringResult(response, buf.toString());
			return null;
			
		}
	}
	
	
	private void stringResult(HttpServletResponse response, String message) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.append(message);
		writer.flush();
		writer.close();
	}


	public void setWxOauthProxyApiUrl(String wxOauthProxyApiUrl) {
		this.wxOauthProxyApiUrl = wxOauthProxyApiUrl;
	}


	public void setWxOauthCallbackUrl(String wxOauthCallbackUrl) {
		this.wxOauthCallbackUrl = wxOauthCallbackUrl;
	}

	public void setSignPrivateKey(String signPrivateKey) {
		this.signPrivateKey = signPrivateKey;
	}


	public String getWxOauthProxyApiUrl() {
		return wxOauthProxyApiUrl;
	}


	public String getWxOauthCallbackUrl() {
		return wxOauthCallbackUrl;
	}


	public String getSignPrivateKey() {
		return signPrivateKey;
	}


	
	

}








