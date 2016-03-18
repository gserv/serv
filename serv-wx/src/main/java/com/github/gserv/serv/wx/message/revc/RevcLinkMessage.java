package com.github.gserv.serv.wx.message.revc;

import org.dom4j.Element;

/**
 * 接收链接消息类型
 * 
 * @author shiying
 *
 */
public class RevcLinkMessage extends AbstractRevcMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6562886914795600022L;
	
	
	/**
	 * 消息标题
	 */
	private String Title;
	
	/**
	 * 消息描述
	 */
	private String Description;
	
	/**
	 * 消息链接
	 */
	private String Url;

	public RevcLinkMessage() {
		super();
	}

	public RevcLinkMessage(String title, String description, String url) {
		super();
		Title = title;
		Description = description;
		Url = url;
	}

	@Override
	protected void parseProperties(Element xmlElement) {
		this.setTitle(xmlElement.elementText("Title"));
		this.setDescription(xmlElement.elementText("Description"));
		this.setUrl(xmlElement.elementText("Url"));
		super.parseProperties(xmlElement);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getTitle() != null) xmlElement.addElement("Title").setText(this.getTitle().toString());
		if (this.getDescription() != null) xmlElement.addElement("Description").setText(this.getDescription().toString());
		if (this.getUrl() != null) xmlElement.addElement("Url").setText(this.getUrl().toString());
		super.setProperties(xmlElement);
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
	
	
	
}
