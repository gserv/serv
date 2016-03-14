package serv.wx.message;

import serv.wx.message.revc.AbstractRevcMessage;
import serv.wx.message.revc.RevcEventMessage;
import serv.wx.message.revc.RevcImageMessage;
import serv.wx.message.revc.RevcLinkMessage;
import serv.wx.message.revc.RevcLocationMessage;
import serv.wx.message.revc.RevcTextMessage;
import serv.wx.message.revc.RevcVideoMessage;
import serv.wx.message.revc.RevcVoiceMessage;
import serv.wx.message.send.AbstractSendMessage;
import serv.wx.message.send.SendImageMessage;
import serv.wx.message.send.SendMusicMessage;
import serv.wx.message.send.SendNewsMessage;
import serv.wx.message.send.SendTextMessage;
import serv.wx.message.send.SendVoiceMessage;

/**
 * 消息类型枚举
 * 
 * @author shiying
 *
 */
public enum MessageType {
		
		text(RevcTextMessage.class, SendTextMessage.class)
	, 	image(RevcImageMessage.class, SendImageMessage.class)
	, 	voice(RevcVoiceMessage.class, SendVoiceMessage.class)
	, 	video(RevcVideoMessage.class, null)
	, 	shortvideo(RevcVideoMessage.class, null)
	,	music(null, SendMusicMessage.class)
	,	news(null, SendNewsMessage.class)
	,	location(RevcLocationMessage.class, null)
	,	link(RevcLinkMessage.class, null)
	,	event(RevcEventMessage.class, null)
	;
	
	private MessageType(Class<? extends AbstractRevcMessage> revcClass,
			Class<? extends AbstractSendMessage> sendClass) {
		this.revcClass = revcClass;
		this.sendClass = sendClass;
	}

	/**
	 * 接收消息类型
	 */
	private Class<? extends AbstractRevcMessage> revcClass;
	
	/**
	 * 发送消息类型
	 */
	private Class<? extends AbstractSendMessage> sendClass;

	public Class<? extends AbstractRevcMessage> getRevcClass() {
		return revcClass;
	}

	public void setRevcClass(Class<? extends AbstractRevcMessage> revcClass) {
		this.revcClass = revcClass;
	}

	public Class<? extends AbstractSendMessage> getSendClass() {
		return sendClass;
	}

	public void setSendClass(Class<? extends AbstractSendMessage> sendClass) {
		this.sendClass = sendClass;
	}
	
	
	
}
