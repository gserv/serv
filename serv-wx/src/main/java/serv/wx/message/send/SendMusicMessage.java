package serv.wx.message.send;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.ResourcesUtils;
import serv.wx.message.MessageType;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.TemplateHandler;
import serv.wx.support.api.media.MediaFileType;
import serv.wx.support.api.media.WxMediaService;
import serv.wx.support.cache.Cache;

/**
 * 音乐消息类型
 * 
 * 
 * @author shiying
 *
 */
public class SendMusicMessage extends AbstractSendMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8079943023056071071L;

	private static final Logger logger = LoggerFactory.getLogger(SendMusicMessage.class);

	/**
	 * 音乐标题
	 */
	private String Title;

	/**
	 * 音乐描述
	 */
	private String Description;

	/**
	 * 音乐链接
	 */
	private String MusicUrl;

	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	private String HQMusicUrl;

	/**
	 * 缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id
	 */
	private String ThumbMediaId;

	/**
	 * 缩略图的媒体的本地地址
	 */
	private String ThumbMediaLocalUrl;

	public SendMusicMessage() {
		super();
		this.setMsgType(MessageType.music);
	}
	
	public SendMusicMessage(String title, String description, String musicUrl,
			String hQMusicUrl, String thumbMediaId) {
		super();
		this.setMsgType(MessageType.music);
		Title = title;
		Description = description;
		MusicUrl = musicUrl;
		HQMusicUrl = hQMusicUrl;
		ThumbMediaId = thumbMediaId;
	}
	
	@Override
	public void expressionHandler(Map<String, Object> data,
			TemplateHandler handler) {
		if (data != null) {
			logger.debug("expression handler, data[{}], object[{}]", data, this);
			if (this.getTitle() != null) this.setTitle(handler.handler(this.getTitle(), data));
			if (this.getDescription() != null) this.setDescription(handler.handler(this.getDescription(), data));
			if (this.getMusicUrl() != null) this.setMusicUrl(handler.handler(this.getMusicUrl(), data));
			if (this.getHQMusicUrl() != null) this.setHQMusicUrl(handler.handler(this.getHQMusicUrl(), data));
		}
		super.expressionHandler(data, handler);
	}
	
	@Override
	protected void setProperties(Element xmlElement) {
		Element music = xmlElement.addElement("Music");
		if (this.getTitle() != null) music.addElement("Title").setText(this.getTitle().toString());
		if (this.getDescription() != null) music.addElement("Description").setText(this.getDescription().toString());
		if (this.getMusicUrl() != null) music.addElement("MusicUrl").setText(this.getMusicUrl().toString());
		if (this.getHQMusicUrl() != null) music.addElement("HQMusicUrl").setText(this.getHQMusicUrl().toString());
		if (this.getThumbMediaId() != null) music.addElement("ThumbMediaId").setText(this.getThumbMediaId().toString());
		super.setProperties(xmlElement);
	}

	@Override
	public void rebuild(WxServiceManager wxServiceManager) {
		if (ThumbMediaId == null && ThumbMediaLocalUrl != null) {
			try {
				if (wxServiceManager != null && wxServiceManager.getWxService(WxMediaService.class) != null) {
					String cacheKey = "music_rebuild_" + ThumbMediaId;
					if (wxServiceManager.getWxService(Cache.class) != null && wxServiceManager.getWxService(Cache.class).get(cacheKey) != null) {
						this.ThumbMediaId = wxServiceManager.getWxService(Cache.class).get(cacheKey);
					} else {
						InputStream input = ResourcesUtils.loadResourcesByUrl(ThumbMediaLocalUrl);
						String separ = ThumbMediaLocalUrl.startsWith("file://") ? File.separator : "/";
						String filename = ThumbMediaLocalUrl.substring(ThumbMediaLocalUrl.lastIndexOf(separ) + separ.length(), ThumbMediaLocalUrl.length());
						this.ThumbMediaId = wxServiceManager.getWxService(WxMediaService.class).upload(MediaFileType.image, input, filename);
						if (wxServiceManager.getWxService(Cache.class) != null) {
							wxServiceManager.getWxService(Cache.class).set(cacheKey, this.ThumbMediaId, 60*60*24*2);
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
	public void buildPushServiceMessage(Map<String, Object> message) {
		Map<String, Object> music = new HashMap<String, Object>();
		music.put("title", this.getTitle());
		music.put("description", this.getThumbMediaId());
		music.put("musicurl", this.getMusicUrl());
		music.put("hqmusicurl", this.getHQMusicUrl());
		music.put("thumb_media_id", this.getThumbMediaId());
		message.put("video", music);
		super.buildPushServiceMessage(message);
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

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getThumbMediaLocalUrl() {
		return ThumbMediaLocalUrl;
	}

	public void setThumbMediaLocalUrl(String thumbMediaLocalUrl) {
		ThumbMediaLocalUrl = thumbMediaLocalUrl;
	}
	
	
}
