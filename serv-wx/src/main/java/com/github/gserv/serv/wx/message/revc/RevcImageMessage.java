package com.github.gserv.serv.wx.message.revc;

import java.util.Date;

import org.dom4j.Element;

import com.github.gserv.serv.wx.message.MessageType;

/**
 * 接收图片消息类型
 * 
 * @author shiying
 *
 */
public class RevcImageMessage extends AbstractRevcMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8466239740392764480L;

	/**
	 * 文本消息内容
	 */
	private String picUrl;

	private String mediaId;

	public RevcImageMessage() {
		super();
	}

	public RevcImageMessage(String toUserName, String fromUserName,
			Date createTime, MessageType msgType, String msgId, String picUrl, String mediaId) {
		super(toUserName, fromUserName, createTime, msgType, msgId);
		this.picUrl = picUrl;
		this.mediaId = mediaId;
	}

	@Override
	protected void parseProperties(Element xmlElement) {
		this.setPicUrl(xmlElement.elementText("PicUrl"));
		this.setMediaId(xmlElement.elementText("MediaId"));
		super.parseProperties(xmlElement);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getPicUrl() != null) xmlElement.addElement("PicUrl").setText(this.getPicUrl().toString());
		if (this.getMediaId() != null) xmlElement.addElement("MediaId").setText(this.getMediaId().toString());
		super.setProperties(xmlElement);
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
	
}
