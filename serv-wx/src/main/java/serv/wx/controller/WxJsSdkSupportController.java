package serv.wx.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import serv.commons.JsonUtils;
import serv.wx.support.api.jssdk.JsSdkConf;
import serv.wx.support.api.jssdk.WxApiJsSdkConfService;

/**
 * 微信JsSDK支持控制器
 * 
 * @author shiying @ caituo
 *
 */
public class WxJsSdkSupportController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(WxJsSdkSupportController.class);
	
	private WxApiJsSdkConfService wxApiJsSdkConfService; 
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//
		String pageurl = request.getParameter("pageurl");
		if (pageurl == null) {
			PrintWriter out = response.getWriter();
			out.write("pageurl is require");
			out.flush();
			out.close();
			return null;
		}
		//
		JsSdkConf conf = wxApiJsSdkConfService.generatorConf(pageurl);
		String json = JsonUtils.toJson(conf);
		//
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
		return null;
	}

	public WxApiJsSdkConfService getWxApiJsSdkConfService() {
		return wxApiJsSdkConfService;
	}

	public void setWxApiJsSdkConfService(WxApiJsSdkConfService wxApiJsSdkConfService) {
		this.wxApiJsSdkConfService = wxApiJsSdkConfService;
	}

	
}
