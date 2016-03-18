package com.github.gserv.serv.wx.message.send;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.api.media.MediaFileType;
import com.github.gserv.serv.wx.support.api.media.WxMediaService;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.ResourcesUtils;
import com.github.gserv.serv.wx.message.MessageType;
import com.github.gserv.serv.wx.support.cache.Cache;

/**
 * 语音消息类型
 * 
 * 额外增加了微信消息中不存在的voiceLocalUrl，当mediaId不存在且voiceLocalUrl存在并有效时，自动调用Media接口上传资源
 * 
 * imgLocalUrl 支持 classpath:// file://
 * 
 * @author shiying
 *
 */
public class SendVoiceMessage extends AbstractSendMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5660724598054801024L;
	
	private static final Logger logger = LoggerFactory.getLogger(SendVoiceMessage.class);

	/**
	 * 媒体ID
	 */
	private String mediaId;
	
	/**
	 * 本地资源地址
	 */
	private String voiceLocalUrl;
	

	public SendVoiceMessage() {
		super();
		this.setMsgType(MessageType.voice);
	}

	public SendVoiceMessage(String toUserName, String fromUserName,
			Date createTime, String mediaId) {
		super(toUserName, fromUserName, createTime, MessageType.voice);
		this.mediaId = mediaId;
	}
	
	@Override
	public void rebuild(WxServiceManager wxServiceManager) {
		if (mediaId == null && voiceLocalUrl != null) {
			try {
				if (wxServiceManager != null && wxServiceManager.getWxService(WxMediaService.class) != null) {
					String cacheKey = "voice_rebuild_" + voiceLocalUrl;
					if (wxServiceManager.getWxService(Cache.class) != null && wxServiceManager.getWxService(Cache.class).get(cacheKey) != null) {
						this.mediaId = wxServiceManager.getWxService(Cache.class).get(cacheKey);
					} else {
						InputStream input = ResourcesUtils.loadResourcesByUrl(voiceLocalUrl);
						String separ = voiceLocalUrl.startsWith("file://") ? File.separator : "/";
						String filename = voiceLocalUrl.substring(voiceLocalUrl.lastIndexOf(separ) + separ.length(), voiceLocalUrl.length());
						this.mediaId = wxServiceManager.getWxService(WxMediaService.class).upload(MediaFileType.voice, input, filename);
						if (wxServiceManager.getWxService(Cache.class) != null) {
							wxServiceManager.getWxService(Cache.class).set(cacheKey, mediaId, 60*60*24*2);
						}
					}
				}
			} catch (Exception e) {
				logger.warn("media rebuid faild.", e);
			}
		}
		super.rebuild(wxServiceManager);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getMediaId() != null) xmlElement.addElement("Voice").addElement("MediaId").setText(this.getMediaId().toString());
		super.setProperties(xmlElement);
	}

	@Override
	public void buildPushServiceMessage(Map<String, Object> message) {
		Map<String, Object> voice = new HashMap<String, Object>();
		voice.put("media_id", this.getMediaId());
		message.put("voice", voice);
		super.buildPushServiceMessage(message);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getVoiceLocalUrl() {
		return voiceLocalUrl;
	}

	public void setVoiceLocalUrl(String voiceLocalUrl) {
		this.voiceLocalUrl = voiceLocalUrl;
	}

	
	
}
