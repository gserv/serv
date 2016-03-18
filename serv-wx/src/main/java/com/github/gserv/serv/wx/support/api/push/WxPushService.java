package com.github.gserv.serv.wx.support.api.push;

import java.util.UUID;

import com.github.gserv.serv.wx.service.manager.WxService;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;

/**
 * 微信消息群发服务
 * @author shiying
 *
 */
public class WxPushService implements WxService {
	
	private WxServiceManager wxServiceManager;
	
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
	
	/**
	 * 创建图文群发任务
	 * @param taskId 任务ID，日志记录需要
	 * @return
	 */
	public synchronized WxPushNewsMessage createWxNewsMessagePush(String taskId) {
		WxPushNewsMessage wxPushNewsMessage = new WxPushNewsMessage();
		wxPushNewsMessage.setWxServiceManager(wxServiceManager);
		wxPushNewsMessage.setMediaUrlBase(mediaUrlBase);
		if (mediaUploadUrl != null) {
			wxPushNewsMessage.setMediaUploadUrl(mediaUploadUrl);
		}
		if (newsMediaUploadUrl != null) {
			wxPushNewsMessage.setNewsMediaUploadUrl(newsMediaUploadUrl);
		}
		if (pushPreviewUrl != null) {
			wxPushNewsMessage.setPushPreviewUrl(pushPreviewUrl);
		}
		if (sendByGroupUrl != null) {
			wxPushNewsMessage.setSendByGroupUrl(sendByGroupUrl);
		}
		if (taskId == null) {
			wxPushNewsMessage.setTaskId(UUID.randomUUID().toString());
		} else {
			wxPushNewsMessage.setTaskId(taskId);
		}
		return wxPushNewsMessage;
	}
	
//	/**
//	 * 创建图文群发任务
//	 * @return
//	 * @throws WxApiInvorkException 
//	 */
//	public WxPushNewsMessage createWxNewsMessagePushAtWxAppId(String wxAppId) throws WxApiInvorkException {
//		WxApiAccessTokenService wxApiAccessTokenService = wxServiceManager.getWxService(WxApiAccessTokenService.class);
//		if (wxApiAccessTokenService == null) {
//			throw new WxApiInvorkException("not found wx app id");
//		}
//		//
//		WxPushNewsMessage wxPushNewsMessage = new WxPushNewsMessage();
//		wxPushNewsMessage.setWxServiceManager(wxServiceManager);
//		wxPushNewsMessage.setMediaUrlBase(mediaUrlBase);
//		return wxPushNewsMessage;
//	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	public String getMediaUploadUrl() {
		return mediaUploadUrl;
	}

	public void setMediaUploadUrl(String mediaUploadUrl) {
		this.mediaUploadUrl = mediaUploadUrl;
	}

	public String getMediaUrlBase() {
		return mediaUrlBase;
	}

	public void setMediaUrlBase(String mediaUrlBase) {
		this.mediaUrlBase = mediaUrlBase;
	}

	public String getNewsMediaUploadUrl() {
		return newsMediaUploadUrl;
	}

	public void setNewsMediaUploadUrl(String newsMediaUploadUrl) {
		this.newsMediaUploadUrl = newsMediaUploadUrl;
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
	
	
	
}
