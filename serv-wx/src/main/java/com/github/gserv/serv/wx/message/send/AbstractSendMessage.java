package com.github.gserv.serv.wx.message.send;

import java.util.Date;
import java.util.Map;

import com.github.gserv.serv.wx.message.AbstractMessage;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.TemplateHandler;
import com.github.gserv.serv.wx.message.MessageType;

/**
 * 抽象的发送消息类型
 * 
 * @author shiying
 *
 */
public class AbstractSendMessage extends AbstractMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2569782765169536698L;

	public AbstractSendMessage() {
		super();
	}

	public AbstractSendMessage(String toUserName, String fromUserName,
			Date createTime, MessageType msgType) {
		super(toUserName, fromUserName, createTime, msgType);
	}
	
	/**
	 * 发送消息重构
	 * 例如针对File类型媒体文件转换为MediaID
	 * @param WxMediaService
	 */
	public void rebuild(WxServiceManager wxServiceManager) {
		
	}
	
	/**
	 * 表达式处理器，针对消息中某些字段，进行表达式与数据的合并
	 * @param message
	 * @param data
	 * @return
	 */
	public void expressionHandler(Map<String, Object> data, TemplateHandler handler) {
	}
	
	/**
	 * 构建客服推送接口消息
	 * @return
	 */
	public void buildPushServiceMessage(Map<String, Object> message) {
		message.put("touser", this.getToUserName());
		message.put("msgtype", this.getMsgType().toString());
	}
	
	
}
