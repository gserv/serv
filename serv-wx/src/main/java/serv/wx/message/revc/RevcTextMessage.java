package serv.wx.message.revc;

import java.util.Date;

import org.dom4j.Element;

import serv.wx.message.MessageType;

/**
 * 接收文本消息类型
 * 
 * @author shiying
 *
 */
public class RevcTextMessage extends AbstractRevcMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6562886914795600022L;
	/**
	 * 文本消息内容
	 */
	private String content;

	public RevcTextMessage() {
		super();
	}

	public RevcTextMessage(String toUserName, String fromUserName,
			Date createTime, MessageType msgType, String msgId, String content) {
		super(toUserName, fromUserName, createTime, msgType, msgId);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	protected void parseProperties(Element xmlElement) {
		this.setContent(xmlElement.elementText("Content"));
		super.parseProperties(xmlElement);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getContent() != null) xmlElement.addElement("Content").setText(this.getContent().toString());
		super.setProperties(xmlElement);
	}
	
	
	
}
