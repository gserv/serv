package serv.wx.support.api.jssdk;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.encry.HashUtils;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenService;

/**
 * JsSDK配置服务
 * 
 * @author shiying
 *
 */
public class WxJsSdkConfDefaultService implements WxApiJsSdkConfService {
	private static final Logger logger = LoggerFactory.getLogger(WxJsSdkConfDefaultService.class);
	
	/**
	 * 服务管理器
	 */
	private WxServiceManager wxServiceManager;
	
	
	@Override
	public JsSdkConf generatorConf(String pageurl) throws WxApiInvorkException {
		// 参数容错
		if (pageurl!=null && pageurl.indexOf("#") != -1) {
			pageurl = pageurl.substring(0, pageurl.indexOf("#"));
		}
		if (pageurl != null && !pageurl.startsWith("http")) {
			pageurl = "http://" + pageurl;
		}
		//
		JsSdkConf conf = new JsSdkConf();
		//
		conf.setAppId(wxServiceManager.getWxService(WxApiAccessTokenService.class).appId());
		conf.setNonceStr(UUID.randomUUID().toString());
		conf.setPageurl(pageurl);
		conf.setTimestamp(""+new Date().getTime()/1000);
		conf.setSignature(
				HashUtils.sha1(
						"jsapi_ticket="+wxServiceManager.getWxService(WxApiJsTicketService.class).jsTicket()
						+"&noncestr="+conf.getNonceStr()
						+"&timestamp="+conf.getTimestamp()
						+"&url="+conf.getPageurl()));
		return conf;
	}


	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}


	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}


	
	
	

}


