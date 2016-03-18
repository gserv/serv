package com.github.gserv.serv.wx.message.send;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.wx.message.MessageType;
import com.github.gserv.serv.wx.support.TemplateHandler;

/**
 * 图文消息类型
 * 
 * @author shiying
 *
 */
public class SendNewsMessage extends AbstractSendMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5660724598054801024L;
	
	private static final Logger logger = LoggerFactory.getLogger(SendNewsMessage.class);
	
	/**
	 * 列表项
	 */
	private List<NewsItem> Articles;
	

	public SendNewsMessage() {
		super();
		this.setMsgType(MessageType.news);
	}

	public SendNewsMessage(String toUserName, String fromUserName,
			Date createTime, List<NewsItem> Articles) {
		super(toUserName, fromUserName, createTime, MessageType.voice);
		this.Articles = Articles;
	}
	
	@Override
	public void expressionHandler(Map<String, Object> data,
			TemplateHandler handler) {
		if (data != null && Articles != null) {
			logger.debug("expression handler, data[{}], object[{}]", data, this);
			for (NewsItem item : Articles) {
				if (item.getTitle() != null) item.setTitle(handler.handler(item.getTitle(), data));
				if (item.getDescription() != null) item.setDescription(handler.handler(item.getDescription(), data));
				if (item.getPicUrl() != null) item.setPicUrl(handler.handler(item.getPicUrl(), data));
				if (item.getUrl() != null) item.setUrl(handler.handler(item.getUrl(), data));
			}
		}
		super.expressionHandler(data, handler);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (Articles != null) {
			xmlElement.addElement("ArticleCount").setText("" + (Articles.size() <= 10 ? Articles.size() : 10));
			Element articles = xmlElement.addElement("Articles");
			for (int i=0; i<Articles.size() && i<=10; i++) {
				Element item = articles.addElement("item");
				if (Articles.get(i).getTitle() != null) item.addElement("Title").setText(Articles.get(i).getTitle());
				if (Articles.get(i).getDescription() != null) item.addElement("Description").setText(Articles.get(i).getDescription());
				if (Articles.get(i).getPicUrl() != null) item.addElement("PicUrl").setText(Articles.get(i).getPicUrl());
				if (Articles.get(i).getUrl() != null) item.addElement("Url").setText(Articles.get(i).getUrl());
			}
		}
		super.setProperties(xmlElement);
	}

	@Override
	public void buildPushServiceMessage(Map<String, Object> message) {
		Map<String, Object> news = new HashMap<String, Object>();
		List<Map<String, Object>> articles = new ArrayList<Map<String, Object>>();
		for (int i=0; i<Articles.size() && i<=10; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("title", Articles.get(i).getTitle());
			item.put("description", Articles.get(i).getDescription());
			item.put("url", Articles.get(i).getPicUrl());
			item.put("picurl", Articles.get(i).getUrl());
			articles.add(item);
		}
		news.put("articles", articles);
		message.put("news", news);
		super.buildPushServiceMessage(message);
	}
	
	public List<NewsItem> getArticles() {
		return Articles;
	}

	public void setArticles(List<NewsItem> articles) {
		Articles = articles;
	}

	/**
	 * 列表项
	 * @author shiying
	 *
	 */
	public static class NewsItem {
		/**
		 * 标题
		 */
		private String Title;
		/**
		 * 文字叙述
		 */
		private String Description;
		/**
		 * 图片链接
		 */
		private String PicUrl;
		/**
		 * 链接地址
		 */
		private String Url;
		public NewsItem() {
			super();
		}
		public NewsItem(String title, String description, String picUrl,
				String url) {
			super();
			Title = title;
			Description = description;
			PicUrl = picUrl;
			Url = url;
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
		public String getPicUrl() {
			return PicUrl;
		}
		public void setPicUrl(String picUrl) {
			PicUrl = picUrl;
		}
		public String getUrl() {
			return Url;
		}
		public void setUrl(String url) {
			Url = url;
		}
		
	}
	
	
}
