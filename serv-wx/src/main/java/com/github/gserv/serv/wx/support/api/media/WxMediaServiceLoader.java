package com.github.gserv.serv.wx.support.api.media;

import com.github.gserv.serv.wx.service.manager.WxServiceLoader;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;

public class WxMediaServiceLoader implements WxServiceLoader<WxMediaService> {
	
	private WxMediaService wxMediaService;
	
	/**
	 * 媒体上传URL
	 */
	private String mediaUploadUrl = "https://api.weixin.qq.com/cgi-bin/media/upload";
	
	/**
	 * 媒体获取URL
	 */
	private String mediaGetUrl = "https://api.weixin.qq.com/cgi-bin/media/get";

	@Override
	public synchronized WxMediaService load(WxServiceManager wxServiceManager) {
		WxMediaService wxMediaService = new WxMediaService();
		wxMediaService.setWxServiceManager(wxServiceManager);
		wxMediaService.setMediaUploadUrl(mediaUploadUrl);
		return wxMediaService;
	}

	public WxMediaService getWxMediaService() {
		return wxMediaService;
	}

	public void setWxMediaService(WxMediaService wxMediaService) {
		this.wxMediaService = wxMediaService;
	}

	public String getMediaUploadUrl() {
		return mediaUploadUrl;
	}

	public void setMediaUploadUrl(String mediaUploadUrl) {
		this.mediaUploadUrl = mediaUploadUrl;
	}

	public String getMediaGetUrl() {
		return mediaGetUrl;
	}

	public void setMediaGetUrl(String mediaGetUrl) {
		this.mediaGetUrl = mediaGetUrl;
	}
	
	

}
