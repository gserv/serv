package com.github.gserv.serv.wx.message.send;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.TemplateHandler;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.wx.message.MessageType;

/**
 * 视频消息类型
 * 
 * 额外增加了微信消息中不存在的videoLocalUrl，当mediaId不存在且videoLocalUrl存在并有效时，自动调用Media接口上传资源
 * 
 * imgLocalUrl 支持 classpath:// file://
 * 
 * @author shiying
 *
 */
public class SendVideoMessage extends AbstractSendMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5660724598054801024L;
	
	private static final Logger logger = LoggerFactory.getLogger(SendVideoMessage.class);

	/**
	 * 媒体ID
	 */
	private String mediaId;

	/**
	 * 预览媒体ID
	 */
	private String thumbMediaId;

	/**
	 * 媒体标题
	 */
	private String title;

	/**
	 * 媒体描述
	 */
	private String description;
	
	/**
	 * 本地资源地址
	 */
	private String videoLocalUrl;
	
	/**
	 * 预览地资源地址
	 */
	private String thumbLocalUrl;
	

	public SendVideoMessage() {
		super();
		this.setMsgType(MessageType.video);
	}

	public SendVideoMessage(String toUserName, String fromUserName,
			Date createTime, String mediaId, String title, String description) {
		super(toUserName, fromUserName, createTime, MessageType.voice);
		this.mediaId = mediaId;
		this.title = title;
		this.description = description;
	}
	
	@Override
	public void expressionHandler(Map<String, Object> data,
			TemplateHandler handler) {
		if (data != null) {
			logger.debug("expression handler, data[{}], object[{}]", data, this);
			if (this.getTitle() != null) this.setTitle(handler.handler(this.getTitle(), data));
			if (this.getDescription() != null) this.setDescription(handler.handler(this.getDescription(), data));
		}
		super.expressionHandler(data, handler);
	}
	
	@Override
	public void rebuild(WxServiceManager wxServiceManager) {
//		if (mediaId == null && videoLocalUrl != null) {
//			try {
//				if (wxServicePackage != null && wxServicePackage.getWxMediaService() != null) {
//					String cacheKey = "video_rebuild_" + videoLocalUrl;
//					if (wxServicePackage.getCache() != null && wxServicePackage.getCache().get(cacheKey) != null) {
//						this.mediaId = wxServicePackage.getCache().get(cacheKey);
//					} else {
//						InputStream input = ResourcesUtils.loadResourcesByUrl(videoLocalUrl);
//						String separ = videoLocalUrl.startsWith("file://") ? File.separator : "/";
//						String filename = videoLocalUrl.substring(videoLocalUrl.lastIndexOf(separ) + separ.length(), videoLocalUrl.length());
//						this.mediaId = wxServicePackage.getWxMediaService().upload(MediaFileType.video, input, filename);
//						if (wxServicePackage.getCache() != null) {
//							wxServicePackage.getCache().set(cacheKey, mediaId, 60*60*24*2);
//						}
//					}
//				}
//			} catch (Exception e) {
//				logger.warn("media rebuid faild.", e);
//			}
//		}
		// TODO 视频接口测试失败
		super.rebuild(wxServiceManager);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		Element voice = xmlElement.addElement("Video");
		if (this.getMediaId() != null) voice.addElement("MediaId").setText(this.getMediaId().toString());
		if (this.getTitle() != null) voice.addElement("Title").setText(this.getTitle().toString());
		if (this.getDescription() != null) voice.addElement("Description").setText(this.getDescription().toString());
		super.setProperties(xmlElement);
	}

	@Override
	public void buildPushServiceMessage(Map<String, Object> message) {
		Map<String, Object> video = new HashMap<String, Object>();
		video.put("media_id", this.getMediaId());
		video.put("thumb_media_id", this.getThumbMediaId());
		video.put("title", this.getMediaId());
		video.put("description", this.getMediaId());
		message.put("video", video);
		super.buildPushServiceMessage(message);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}


	public String getVideoLocalUrl() {
		return videoLocalUrl;
	}

	public void setVideoLocalUrl(String videoLocalUrl) {
		this.videoLocalUrl = videoLocalUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getThumbLocalUrl() {
		return thumbLocalUrl;
	}

	public void setThumbLocalUrl(String thumbLocalUrl) {
		this.thumbLocalUrl = thumbLocalUrl;
	}

	
	
}
