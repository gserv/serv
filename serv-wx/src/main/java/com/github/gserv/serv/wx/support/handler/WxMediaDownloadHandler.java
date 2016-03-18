package com.github.gserv.serv.wx.support.handler;

import java.io.File;
import java.util.List;

import com.github.gserv.serv.wx.message.revc.AbstractRevcMessage;
import com.github.gserv.serv.wx.service.accept.NoticeAcceptContext;
import com.github.gserv.serv.wx.support.api.media.WxMediaService;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.wx.message.send.AbstractSendMessage;
import com.github.gserv.serv.wx.support.MessageHandlerException;
import com.github.gserv.serv.wx.support.WxMessageHandler;

/**
 * 媒体下载处理器
 * 
 * @author shiying
 *
 */
public class WxMediaDownloadHandler implements WxMessageHandler {
	private static final Logger logger = LoggerFactory.getLogger(WxMediaDownloadHandler.class);
	
	/**
	 * 保存路径
	 */
	private String savePath;
	
	/**
	 * 转储的媒体ID属性列表
	 */
	private List<String> mediaIdProperties;
	
	/**
	 * 资源处理器
	 */
	private WxMediaService wxMediaService;

	@Override
	public AbstractSendMessage messageHandler(AbstractRevcMessage revc, NoticeAcceptContext noticeAcceptContext)
			throws MessageHandlerException {
		if (wxMediaService == null) {
			logger.warn("wxMediaService is null.");
			return null;
		}
		if (mediaIdProperties == null) {
			logger.warn("mediaIdProperties is null");
			return null;
		}
		if (savePath == null) {
			logger.warn("savePath is null");
			return null;
		}
		File path = new File(savePath);
		path.mkdirs();
		for (String mediaIdPropertiesName : mediaIdProperties) {
			try {
				String mediaId = BeanUtils.getProperty(revc, mediaIdPropertiesName);
				File cache = wxMediaService.getMediaFile(mediaId);
				File file = new File(path, mediaId + "_" + cache.getName());
				cache.renameTo(file);
				logger.info("dump weixin message media, mediaId[{}], file[{}]", mediaId, file);
				
			} catch (Exception e) {
				logger.warn("media download faild. mediaIdPropertiesName[{}], revc[{}]", mediaIdPropertiesName, revc.toString());
			}
		}
		return null;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setMediaIdProperties(List<String> mediaIdProperties) {
		this.mediaIdProperties = mediaIdProperties;
	}

	public void setWxMediaService(WxMediaService wxMediaService) {
		this.wxMediaService = wxMediaService;
	}

	
}
