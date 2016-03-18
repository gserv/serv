package com.github.gserv.serv.wx.support.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.wx.message.revc.AbstractRevcMessage;
import com.github.gserv.serv.wx.message.send.AbstractSendMessage;
import com.github.gserv.serv.wx.service.accept.NoticeAcceptContext;
import com.github.gserv.serv.wx.support.MessageHandlerException;
import com.github.gserv.serv.wx.support.WxMessageHandler;

/**
 * 链式处理器
 * 
 * @author shiying
 *
 */
public class WxMessageChainHandler implements WxMessageHandler {
	private static final Logger logger = LoggerFactory.getLogger(WxMessageChainHandler.class);
	
	/**
	 * 处理器列表
	 */
	private List<WxMessageHandler> handlerList;

	@Override
	public AbstractSendMessage messageHandler(AbstractRevcMessage revc, NoticeAcceptContext noticeAcceptContext)
			throws MessageHandlerException {
		if (handlerList != null) {
			for (WxMessageHandler handler : handlerList) {
				try {
					AbstractSendMessage sendMessage = handler.messageHandler(revc, noticeAcceptContext);
					if (sendMessage != null) {
						logger.info("hit handler [{}] as message [{}], send message [{}]", 
								handler.getClass().toString(), JsonUtils.toJson(revc), JsonUtils.toJson(sendMessage));
						return sendMessage;
					}
				} catch (Exception e) {
					logger.warn("treatment handler by ["+handler.getClass()+"] exception. revc ["+revc.toString()+"]", e);
				}
			}
		}
		return null;
	}

	public void setHandlerList(List<WxMessageHandler> handlerList) {
		this.handlerList = handlerList;
	}

	
	
	
}
