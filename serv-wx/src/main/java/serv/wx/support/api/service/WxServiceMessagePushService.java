package serv.wx.support.api.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.HttpUtils;
import serv.commons.JsonUtils;
import serv.wx.message.send.AbstractSendMessage;
import serv.wx.service.manager.WxService;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenService;

/**
 * 微信客服消息推送接口
 * 
 * @author shiying
 *
 */
public class WxServiceMessagePushService implements WxService {
	private static final Logger logger = LoggerFactory.getLogger(WxServiceMessagePushService.class);
	
	private WxServiceManager wxServiceManager;
	
	/**
	 * 客服消息发送URL
	 */
	private String serviceSendUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
	
	/**
	 * 推送微信消息
	 * @param sendMessage 推送消息
	 */
	public void sendMessage(AbstractSendMessage sendMessage) throws WxApiInvorkException {
		sendMessage(sendMessage, null);	
	}
	
	/**
	 * 推送微信消息
	 * @param sendMessage 推送消息
	 * @param kf_account 以某个客服帐号来发消息，不存在则为空
	 */
	public void sendMessage(AbstractSendMessage sendMessage, String kf_account) throws WxApiInvorkException {
		Map<String, Object> message = new HashMap<String, Object>();
		// 构建客服消息
		sendMessage.buildPushServiceMessage(message);
		// 添加客服账号
		if (kf_account != null) {
			Map<String, Object> customservice = new HashMap<String, Object>();
			customservice.put("kf_account", kf_account);
			message.put("customservice", customservice);
		}
		//
		String url = serviceSendUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
		Map<String, Object> serviceMessage = new HashMap<String, Object>();
		sendMessage.buildPushServiceMessage(serviceMessage);
		String messageBody =  JsonUtils.toJson(serviceMessage);
		logger.debug("push weixin service message, body [{}]", messageBody);
		// fire
		Map<String, Object> res = null;
		try {
			String json = HttpUtils.post(url, messageBody);
			res = JsonUtils.parseJsonMap(json);
		} catch (IOException e) {
			throw new WxApiInvorkException("fire http request faild", e);
		}
		// rs
		if (res == null) {
			throw new WxApiInvorkException("invork http request is null");
		}
		if (res.get("errcode") == null || !res.get("errcode").equals("0")) {
			throw new WxApiInvorkException("invork weixin service api faild. response["+res+"]");
		}
		logger.info("push weixin service message success. message [{}]", messageBody);
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	

}
