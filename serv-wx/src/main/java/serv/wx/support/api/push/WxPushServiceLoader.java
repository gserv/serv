package serv.wx.support.api.push;

import serv.wx.service.manager.WxServiceLoader;
import serv.wx.service.manager.WxServiceManager;

public class WxPushServiceLoader implements WxServiceLoader<WxPushService> {
	
	/**
	 * 图文媒体上传的URL地址
	 */
	private String newsMediaUploadUrl = null; //"https://api.weixin.qq.com/cgi-bin/media/uploadnews";
	
	/**
	 * 媒体资源上传URL
	 */
	private String mediaUploadUrl = null; //"https://api.weixin.qq.com/cgi-bin/media/uploadimg";
	
	/**
	 * 预览推送地址
	 */
	private String pushPreviewUrl = null; //"https://api.weixin.qq.com/cgi-bin/message/mass/preview";
	
	/**
	 * 全部发送/通过分组发送接口地址
	 */
	private String sendByGroupUrl = null; //"https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
	
	/**
	 * 基础URL地址
	 */
	private String mediaUrlBase = "";

	@Override
	public synchronized WxPushService load(WxServiceManager wxServiceManager) {
		WxPushService wxPushService = new WxPushService();
		wxPushService.setWxServiceManager(wxServiceManager);
		wxPushService.setMediaUploadUrl(mediaUploadUrl);
		wxPushService.setMediaUrlBase(mediaUrlBase);
		wxPushService.setNewsMediaUploadUrl(newsMediaUploadUrl);
		wxPushService.setPushPreviewUrl(pushPreviewUrl);
		wxPushService.setSendByGroupUrl(sendByGroupUrl);
		return wxPushService;
	}

	public String getNewsMediaUploadUrl() {
		return newsMediaUploadUrl;
	}

	public void setNewsMediaUploadUrl(String newsMediaUploadUrl) {
		this.newsMediaUploadUrl = newsMediaUploadUrl;
	}

	public String getMediaUploadUrl() {
		return mediaUploadUrl;
	}

	public void setMediaUploadUrl(String mediaUploadUrl) {
		this.mediaUploadUrl = mediaUploadUrl;
	}

	public String getPushPreviewUrl() {
		return pushPreviewUrl;
	}

	public void setPushPreviewUrl(String pushPreviewUrl) {
		this.pushPreviewUrl = pushPreviewUrl;
	}

	public String getSendByGroupUrl() {
		return sendByGroupUrl;
	}

	public void setSendByGroupUrl(String sendByGroupUrl) {
		this.sendByGroupUrl = sendByGroupUrl;
	}

	public String getMediaUrlBase() {
		return mediaUrlBase;
	}

	public void setMediaUrlBase(String mediaUrlBase) {
		this.mediaUrlBase = mediaUrlBase;
	}
	
	

}
