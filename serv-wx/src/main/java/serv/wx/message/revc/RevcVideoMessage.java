package serv.wx.message.revc;

import java.util.Date;

import org.dom4j.Element;

import serv.wx.message.MessageType;

/**
 * 接收视频消息类型
 * 
 * @author shiying
 *
 */
public class RevcVideoMessage extends AbstractRevcMessage {
	
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
	private String ThumbMediaId;
	

	public RevcVideoMessage() {
		super();
	}

	public RevcVideoMessage(String toUserName, String fromUserName,
			Date createTime, MessageType msgType, String msgId, String MediaId, String ThumbMediaId) {
		super(toUserName, fromUserName, createTime, msgType, msgId);
		this.MediaId = MediaId;
		this.ThumbMediaId = ThumbMediaId;
	}

	@Override
	protected void parseProperties(Element xmlElement) {
		this.setMediaId(xmlElement.elementText("MediaId"));
		this.setThumbMediaId(xmlElement.elementText("ThumbMediaId"));
		super.parseProperties(xmlElement);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getMediaId() != null) xmlElement.addElement("MediaId").setText(this.getMediaId().toString());
		if (this.getThumbMediaId() != null) xmlElement.addElement("ThumbMediaId").setText(this.getThumbMediaId().toString());
		super.setProperties(xmlElement);
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	
	
	
}
