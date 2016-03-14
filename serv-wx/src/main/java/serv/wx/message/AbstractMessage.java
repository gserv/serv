package serv.wx.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 基本的消息类型，包含了微信消息交互中的基本字段
 * 
 * @author shiying
 *
 */
public abstract class AbstractMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 966308080403596839L;
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractMessage.class);
	
	/**
	 * 目标用户
	 */
	private String toUserName;
	
	/**
	 * 来源用户
	 */
	private String fromUserName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 消息类型
	 */
	private MessageType msgType;
	
	
	public AbstractMessage() {
		super();
	}
	

	public AbstractMessage(String toUserName, String fromUserName,
			Date createTime, MessageType msgType) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.msgType = msgType;
	}
	

	/**
	 * 转换为微信XML消息
	 * @return
	 */
	public String toWxXml() {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("xml");
		// 设置属性
		setProperties(rootElement);
		// 转换为XML
		OutputFormat xmlFormat = new OutputFormat();  
        xmlFormat.setEncoding("UTF-8");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Writer stringWriter = new OutputStreamWriter(bytes);
		XMLWriter xmlWriter = new XMLWriter(stringWriter, xmlFormat);
		try {
			xmlWriter.write(document);
		} catch (IOException e) {
			logger.warn("XML文档创建异常", e);
			return "<exception>";
		}
		return document.asXML();
	}
	
	/**
	 * 解析属性
	 * @param xmlElement
	 */
	protected void parseProperties(Element xmlElement) {
		this.setCreateTime(new Date(Long.parseLong(xmlElement.elementText("CreateTime")) * 1000));
		this.setFromUserName(xmlElement.elementText("FromUserName"));
		this.setMsgType(MessageType.valueOf(xmlElement.elementText("MsgType")));
		this.setToUserName(xmlElement.elementText("ToUserName"));
	}
	
	
	/**
	 * 向XML添加属性
	 * @param xmlElement
	 */
	protected void setProperties(Element xmlElement) {
		xmlElement.addElement("CreateTime").setText(""+(int) (this.getCreateTime().getTime() / 1000));
		xmlElement.addElement("FromUserName").setText(this.getFromUserName());
		xmlElement.addElement("MsgType").setText(this.getMsgType().toString());
		xmlElement.addElement("ToUserName").setText(this.getToUserName());
	}
	
	
	

	@Override
	public String toString() {
		return this.toWxXml().replaceAll("\n", "");
	}


	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	
	

}
