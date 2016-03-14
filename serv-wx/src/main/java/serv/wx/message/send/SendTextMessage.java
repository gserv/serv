package serv.wx.message.send;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.wx.message.MessageType;
import serv.wx.support.TemplateHandler;

/**
 * 文本消息类型
 * 
 * 
 * @author shiying
 *
 */
public class SendTextMessage extends AbstractSendMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8079943023056071071L;

	private static final Logger logger = LoggerFactory.getLogger(SendTextMessage.class);

	/**
	 * 文本消息内容
	 */
	private String content;
	
	@Override
	public void expressionHandler(Map<String, Object> data, TemplateHandler handler) {
		if (data != null) {
			logger.debug("expression handler, data[{}], object[{}]", data, this);
			if (this.getContent() != null) this.setContent(handler.handler(this.getContent(), data));
		}
		super.expressionHandler(data, handler);
	}

	@Override
	public void buildPushServiceMessage(Map<String, Object> message) {
		Map<String, Object> text = new HashMap<String, Object>();
		text.put("content", this.getContent());
		message.put("text", text);
		super.buildPushServiceMessage(message);
	}



	public SendTextMessage() {
		super();
		this.setMsgType(MessageType.text);
	}

	public SendTextMessage(String toUserName, String fromUserName,
			Date createTime, String content) {
		super(toUserName, fromUserName, createTime, MessageType.text);
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getContent() != null) xmlElement.addElement("Content").setText(this.getContent().toString());
		super.setProperties(xmlElement);
	}
	
}
