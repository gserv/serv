package serv.wx.message.revc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import serv.wx.message.AbstractMessage;
import serv.wx.message.MessageType;
import serv.wx.message.XmlMessageParseException;

/**
 * 抽象的接收消息类型
 * 
 * @author shiying
 *
 */
public class AbstractRevcMessage extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 689134825115672099L;
	/**
	 * 消息ID
	 */
	private String msgId;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public AbstractRevcMessage() {
		super();
	}

	public AbstractRevcMessage(String toUserName, String fromUserName,
			Date createTime, MessageType msgType, String msgId) {
		super(toUserName, fromUserName, createTime, msgType);
		this.msgId = msgId;
	}

	@Override
	protected void parseProperties(Element xmlElement) {
		this.msgId = xmlElement.elementText("MsgId") != null 
				? xmlElement.elementText("MsgId") 
				: xmlElement.elementText("FromUserName") + xmlElement.elementText("CreateTime");
		super.parseProperties(xmlElement);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		xmlElement.addElement("MsgId").setText(this.getMsgId().toString());
		super.setProperties(xmlElement);
	}
	
	
	/**
	 * 解析从微信接收的XML消息
	 * @param xml
	 * @return
	 */
	public static AbstractRevcMessage parseWxRevcXml(String xml) throws XmlMessageParseException {
		SAXReader saxReader = new SAXReader();
		InputStream xmlstream = null;
		try {
			xmlstream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new XmlMessageParseException("XML maybe not UTF-8 encode", e);
		}
		try {
			Document document = saxReader.read(xmlstream);
			Element rootElement = document.getRootElement();
			// 获取消息类型
			MessageType messageType = MessageType.valueOf(
					rootElement.elementText("MsgType"));
			// 实例化接收对象
			AbstractRevcMessage abstractMessage = messageType.getRevcClass().newInstance();
			// 解析属性
			abstractMessage.parseProperties(rootElement);
			return abstractMessage;
			
		} catch (Exception e) {
			throw new XmlMessageParseException("XML parse exception", e);
		} finally {
			try {
				xmlstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
