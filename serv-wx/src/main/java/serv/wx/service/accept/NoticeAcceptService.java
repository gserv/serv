package serv.wx.service.accept;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.JsonUtils;
import serv.commons.encry.HashUtils;
import serv.wx.message.revc.AbstractRevcMessage;
import serv.wx.message.send.AbstractSendMessage;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.service.multiple.WxServiceMultipleManager;
import serv.wx.support.MessageHandlerException;
import serv.wx.support.WxMessageHandler;
import serv.wx.support.cache.Cache;

/**
 * 微信通知受理服务
 * @author shiying
 *
 */
public class NoticeAcceptService {
	private static final Logger logger = LoggerFactory.getLogger(NoticeAcceptService.class);
	
	/**
	 * 消息分发器
	 */
	private WxMessageHandler wxMessageHandler;
	
	/**
	 * 服务管理器（优先）
	 */
	private WxServiceManager wxServiceManager;
	
	/**
	 * 支持多应用的服务管理器
	 */
	private WxServiceMultipleManager wxServiceMultipleManager;
	
	
	/**
	 * 获取服务管理器
	 * @param accessid
	 * @param sign
	 * @return
	 */
	private WxServiceManager getWxServiceManager(NoticeAcceptContext noticeAcceptContext) {
		if (noticeAcceptContext.getAccessid() == null) {
			if (wxServiceManager == null) {
				return null;
			} else {
				return wxServiceManager;
			}
		} else {
			if (wxServiceMultipleManager == null) {
				return null;
			} else {
				String _sign = HashUtils.md5("_" + noticeAcceptContext.getAccessid());
				if (!_sign.equalsIgnoreCase(noticeAcceptContext.getSign())) {
					logger.warn("sign checked faild. sign[{}], expectation[{}]", noticeAcceptContext.getSign(), _sign);
					return null;
				}
				return wxServiceMultipleManager.getWxServiceManager(noticeAcceptContext.getAccessid());
			}
		}
	}
	
	
	/**
	 * 微信通知消息受理
	 * 
	 * 
	 * @param accessid 系统的接入ID，通过该ID查找访问的微信配置
	 * @param sign 接入ID签名，用来验证accessid的有效性
	 * @param signature 签名（微信接入验证）
	 * @param timestamp 时间戳（微信接入验证）
	 * @param nonce 随机数（微信接入验证）
	 * @param echostr 唯一字符串（微信接入验证）
	 * @param resbody 接收内容
	 * @return
	 * @throws MessageHandlerException
	 */
	public String accept(NoticeAcceptContext noticeAcceptContext
	) throws MessageHandlerException {
		// 获取服务管理器
		WxServiceManager wxServiceManager = getWxServiceManager(noticeAcceptContext);
		if (wxServiceManager == null) {
			throw new MessageHandlerException("not found weixin service message, accessid["
					+noticeAcceptContext.getAccessid()+"], sign["+noticeAcceptContext.getSign()+"]");
		}
		// 
		if (noticeAcceptContext.getEchostr() != null) {
			logger.info("weixin access validate, noticeAcceptContext[{}]", JsonUtils.toJson(noticeAcceptContext));
			// 计算签名
			List<String> ss = new ArrayList<String>();
			ss.add(noticeAcceptContext.getTimestamp());
			ss.add(noticeAcceptContext.getNonce());
			ss.add(wxServiceManager.getToken());
			Collections.sort(ss);
			StringBuilder builder = new StringBuilder();
			for(String s : ss) {
				builder.append(s);
			}
			@SuppressWarnings("deprecation")
			String _signature = DigestUtils.shaHex(builder.toString());
			if (noticeAcceptContext.getSignature().equalsIgnoreCase(_signature)) {
				return noticeAcceptContext.getEchostr();
				
			} else {
				logger.warn("weixin access validate faild. signature[{}], expectation[{}]", noticeAcceptContext.getSignature(), _signature);
				return "faild";
				
			}
			
		} else {
			// 接收消息
			if (noticeAcceptContext.getReqbody() == null || noticeAcceptContext.getReqbody().trim().length() <= 0) {
				logger.warn("Illegal weixin message  [{}]", noticeAcceptContext.getReqbody());
				return "Illegal weixin message, " + noticeAcceptContext.getReqbody();
			}
			logger.debug("revc weixin message  [{}]", noticeAcceptContext.getReqbody());
			AbstractRevcMessage revcObj = AbstractRevcMessage.parseWxRevcXml(noticeAcceptContext.getReqbody());
			// 检查消息是否已经处理
			String cacheKey = "revc_message_" + revcObj.getMsgId();
			if (wxServiceManager.getWxService(Cache.class).get(cacheKey) != null) {
				logger.warn("weixin message already accept, message [{}]", revcObj.toString());
				return null;
			}
			wxServiceManager.getWxService(Cache.class).set(cacheKey, revcObj);
			// 分发消息
			try {
				if (wxMessageHandler == null) {
					logger.warn("not found wxMessage handler");
					return null;
				}
				// 交给处理器处理
				AbstractSendMessage sendObj = wxMessageHandler.messageHandler(revcObj, noticeAcceptContext);
				if (sendObj == null) {
					logger.debug("weixin message handle complate, but result message is null.");
					return null;
				}
				// 全局属性赋值
				sendObj.setCreateTime(new Date());
				sendObj.setFromUserName(revcObj.getToUserName());
				sendObj.setToUserName(revcObj.getFromUserName());
				//
				logger.debug("weixin message handle complate, sendMessage [{}]", sendObj.toWxXml());
				// rebuild
				sendObj.rebuild(wxServiceManager);
				//
				logger.debug("weixin message rebuild complate, sendMessage [{}]", sendObj.toWxXml());
				//
				return sendObj.toWxXml();
				
			} catch (MessageHandlerException e) {
				logger.warn("weixin message handle exception.", e);
				return null;
				
			}
		}
	}


	public void setWxMessageHandler(WxMessageHandler wxMessageHandler) {
		this.wxMessageHandler = wxMessageHandler;
	}


	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}


	public void setWxServiceMultipleManager(
			WxServiceMultipleManager wxServiceMultipleManager) {
		this.wxServiceMultipleManager = wxServiceMultipleManager;
	}

	
	
}
