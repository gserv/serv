package com.github.gserv.serv.wx.message.revc;

import java.util.Date;

import org.dom4j.Element;

import com.github.gserv.serv.wx.message.MessageType;

/**
 * 接收语音消息类型
 * 
 * @author shiying
 *
 */
public class RevcVoiceMessage extends AbstractRevcMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8466239740392764480L;

	/**
	 * 媒体ID
	 */
	private String MediaId;

	/**
	 * 语音格式
	 */
	private String Format;
	
	/**
	 * 语音识别内容（需要启用）
	 */
	private String Recognition;

	public RevcVoiceMessage() {
		super();
	}

	public RevcVoiceMessage(String toUserName, String fromUserName,
			Date createTime, MessageType msgType, String msgId, String MediaId, String Format, String Recognition) {
		super(toUserName, fromUserName, createTime, msgType, msgId);
		this.MediaId = MediaId;
		this.Format = Format;
		this.Recognition = Recognition;
	}

	@Override
	protected void parseProperties(Element xmlElement) {
		this.setMediaId(xmlElement.elementText("MediaId"));
		this.setFormat(xmlElement.elementText("Format"));
		this.setRecognition(xmlElement.elementText("Recognition"));
		super.parseProperties(xmlElement);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getMediaId() != null) xmlElement.addElement("MediaId").setText(this.getMediaId().toString());
		if (this.getFormat() != null) xmlElement.addElement("Format").setText(this.getFormat().toString());
		if (this.getRecognition() != null) xmlElement.addElement("Recognition").setText(this.getRecognition().toString());
		super.setProperties(xmlElement);
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	
	
	
}
