package serv.wx.message.send;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.ResourcesUtils;
import serv.wx.message.MessageType;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.api.media.MediaFileType;
import serv.wx.support.api.media.WxMediaService;
import serv.wx.support.cache.Cache;

/**
 * 文本消息类型
 * 
 * 额外增加了微信消息中不存在的imgLocalUrl，当mediaId不存在且imgLocalUrl存在并有效时，自动调用Media接口上传资源
 * 
 * imgLocalUrl 支持 classpath:// file://
 * 
 * @author shiying
 *
 */
public class SendImageMessage extends AbstractSendMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5660724598054801024L;
	
	private static final Logger logger = LoggerFactory.getLogger(SendImageMessage.class);

	/**
	 * 媒体ID
	 */
	private String mediaId;
	
	/**
	 * 图片URL地址
	 */
	private String imgLocalUrl;

	public SendImageMessage() {
		super();
		this.setMsgType(MessageType.image);
	}

	public SendImageMessage(String toUserName, String fromUserName,
			Date createTime, String mediaId) {
		super(toUserName, fromUserName, createTime, MessageType.image);
		this.mediaId = mediaId;
	}
	
	@Override
	public void rebuild(WxServiceManager wxServiceManager) {
		if (mediaId == null && imgLocalUrl != null) {
			try {
				if (wxServiceManager != null && wxServiceManager.getWxService(WxMediaService.class) != null) {
					String cacheKey = "image_rebuild_" + imgLocalUrl;
					if (wxServiceManager.getWxService(Cache.class) != null && wxServiceManager.getWxService(Cache.class).get(cacheKey) != null) {
						this.mediaId = wxServiceManager.getWxService(Cache.class).get(cacheKey);
					} else {
						InputStream input = ResourcesUtils.loadResourcesByUrl(imgLocalUrl);
						String separ = imgLocalUrl.startsWith("file://") ? File.separator : "/";
						String filename = imgLocalUrl.substring(imgLocalUrl.lastIndexOf(separ) + separ.length(), imgLocalUrl.length());
						this.mediaId = wxServiceManager.getWxService(WxMediaService.class).upload(MediaFileType.image, input, filename);
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
		if (this.getMediaId() != null) xmlElement.addElement("Image").addElement("MediaId").setText(this.getMediaId().toString());
		super.setProperties(xmlElement);
	}

	@Override
	public void buildPushServiceMessage(Map<String, Object> message) {
		Map<String, Object> image = new HashMap<String, Object>();
		image.put("media_id", this.getMediaId());
		message.put("image", image);
		super.buildPushServiceMessage(message);
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getImgLocalUrl() {
		return imgLocalUrl;
	}

	public void setImgLocalUrl(String imgLocalUrl) {
		this.imgLocalUrl = imgLocalUrl;
	}

	
}
