package serv.wx.support;

import serv.wx.message.revc.AbstractRevcMessage;
import serv.wx.message.send.AbstractSendMessage;
import serv.wx.service.accept.NoticeAcceptContext;

/**
 * 微信消息分发器
 * 
 * @author shiying
 *
 */
public interface WxMessageHandler {
	
	/**
	 * 处理消息
	 * 
	 * @param revc 接收消息
	 * @return 返回消息，当返回null，继续处理链
	 * @throws MessageHandlerException
	 */
	public AbstractSendMessage messageHandler(
			AbstractRevcMessage revc, NoticeAcceptContext noticeAcceptContext
	) throws MessageHandlerException;
	
}
