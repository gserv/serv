package serv.wx.support.api.push;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.HttpUtils;
import serv.commons.JsonUtils;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import serv.wx.support.api.media.MediaFileType;
import serv.wx.support.api.media.WxMediaService;

/**
 * 图文消息群发任务
 * 
 * @author shiying
 *
 */
public class WxPushNewsMessage {
	private static final Logger logger = LoggerFactory.getLogger(WxPushNewsMessage.class);
	
	private WxServiceManager wxServiceManager;
	
	/**
	 * 图文媒体上传的URL地址
	 */
	private String newsMediaUploadUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews";
	
	/**
	 * 媒体资源上传URL
	 */
	private String mediaUploadUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
	
	/**
	 * 预览推送地址
	 */
	private String pushPreviewUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/preview";
	
	/**
	 * 全部发送/通过分组发送接口地址
	 */
	private String sendByGroupUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";
	
	/**
	 * 菜单项列表
	 */
	private List<WxPushNewsMessageItem> items = new ArrayList<WxPushNewsMessageItem>();
	
	/**
	 * 图文媒体ID，上传后获得
	 */
	private String newsMediaId;
	
	/**
	 * 基础URL地址
	 */
	private String mediaUrlBase = "";
	
	/**
	 * 调试状态
	 */
	private boolean debugPush = false;
	
	/**
	 * 错误重试次数
	 */
	private int uploadFaildRetryTimes = 10;
	
	private String taskId;


	/**
	 * 设置调试状态
	 * @param debugPush
	 * @return
	 */
	public WxPushNewsMessage setDebugPush(boolean debugPush) {
		this.debugPush = debugPush;
		return this;
	}
	
	
	/**
	 * 上传媒体文件
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws WxApiInvorkException 
	 */
	public String uploadMedia(String url) throws IOException, WxApiInvorkException {
		logger.debug("start image media upload. taskId[{}], url[{}]", taskId, url);
		url = url.startsWith("http") ? url : (mediaUrlBase + url);
		File tmpFile = HttpUtils.download(url);
		logger.debug("temp file download complate，taskId[{}], file[{}]", taskId, tmpFile);
		//
		String apiUrl = mediaUploadUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
		Map<String, File> parts = new HashMap<String, File>();
		parts.put("media", tmpFile);
		logger.debug("upload media start. taskId[{}]", taskId);
		String json = HttpUtils.post(apiUrl, parts, new HashMap<String, String>());
		logger.debug("upload media complate. taskId[{}], url[{}], tmpFile[{}], response[{}]", taskId, url, tmpFile, json);
		Map<String, Object> obj = JsonUtils.parseJsonMap(json);
		if (obj.get("url") == null) {
			throw new WxApiInvorkException("media upload request faild. res ["+JsonUtils.toJson(obj)+"]"); 
		}
		return (String) obj.get("url");
	}
	
	
	/**
	 * 增加群发图文项
	 * @param thumb_url 缩略图地址
	 * @param title 标题
	 * @param digest 描述摘要
	 * @param content 内容正文
	 * @param show_cover_pic 是否显示封面，1为显示，0为不显示
	 * @param author 图文消息的作者
	 * @param content_source_url 在图文消息页面点击“阅读原文”后的页面
	 * @return
	 * @throws WxApiInvorkException 
	 * @throws IOException 
	 */
	public WxPushNewsMessage addNewsItem(
			String thumb_url, String title, String digest, 
			String content, 
			String show_cover_pic, 
			String author, String content_source_url) throws WxApiInvorkException, IOException {
		if (items.size() > 10) {
			throw new WxApiInvorkException("news item no more than 10");
		}
		logger.debug("addNewsItem, taskId[{}], thumb_url[{}], title[{}], digest[{}], content[{}], show_cover_pic[{}], "
				+ "author[{}], content_source_url[{}]", 
				taskId, thumb_url, title, digest, content, show_cover_pic, author, content_source_url);
		// 容错计数器
		int tryTime = 0;
		// 预处理
		String thumb_media_id = null;
		while (thumb_media_id == null && tryTime < uploadFaildRetryTimes) {
			try {
				if (tryTime >= 1) {
					logger.warn("retry upload, taskId["+taskId+"], times " + tryTime);
				}
				thumb_media_id = wxServiceManager.getWxService(WxMediaService.class).upload(MediaFileType.image, thumb_url, true);
			} catch (Exception e) {
				logger.warn("upload file. taskId["+taskId+"], times["+tryTime+"]", e);
				tryTime++;
				if (tryTime >= uploadFaildRetryTimes) {
					throw new WxApiInvorkException(e);
				}
			}
		}
		Matcher matcher = Pattern.compile("<img[^>]*\\s+src=['\"]([^'\">]*?)['\"][^>]*>", Pattern.CASE_INSENSITIVE).matcher(content);
		while (matcher.find()) {
			String srcUrl = matcher.group(1);
			//
			String wxUrl = null;
			tryTime = 0;
			while (wxUrl == null && tryTime < uploadFaildRetryTimes) {
				try {
					if (tryTime >= 1) {
						logger.warn("retry upload, taskId["+taskId+"], times " + tryTime);
					}
					wxUrl = uploadMedia(srcUrl);
				} catch (Exception e) {
					logger.warn("upload file. taskId["+taskId+"], times["+tryTime+"]", e);
					tryTime++;
					if (tryTime >= uploadFaildRetryTimes) {
						throw new WxApiInvorkException(e);
					}
				}
			}
			logger.debug("media upload success, taskId[{}], srcUrl[{}], wxUrl[{}]", taskId, srcUrl, wxUrl);
			content = content.replaceFirst(srcUrl, wxUrl);
		}
		//
		WxPushNewsMessageItem item = new WxPushNewsMessageItem();
		item.setAuthor(author);
		item.setContent(content);
		item.setContent_source_url(content_source_url);
		item.setDigest(digest);
		item.setShow_cover_pic(show_cover_pic);
		item.setThumb_media_id(thumb_media_id);
		item.setTitle(title);
		items.add(item);
		return this;
	}
	
	
	/**
	 * 上传图文消息
	 * @return
	 * @throws IOException 
	 * @throws WxApiInvorkException 
	 */
	public WxPushNewsMessage upload() throws IOException, WxApiInvorkException {
		logger.info("upload news media, taskId[{}], items[{}]", taskId, JsonUtils.toJson(items));
		if (this.newsMediaId != null && !this.newsMediaId.equals("")) {
			return this;
		}
		Map<String, List<WxPushNewsMessageItem>> data = new HashMap<String, List<WxPushNewsMessageItem>>();
		data.put("articles", items);
		String json = JsonUtils.toJson(data);
		String url = newsMediaUploadUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
		logger.debug("upload weixin news media start, taskId[{}], data[{}], url[{}]", taskId, json, url);
		String res = null;
		int tryTime = 0;
		while (res == null && tryTime < uploadFaildRetryTimes) {
			try {
				if (tryTime >= 1) {
					logger.warn("retry upload, taskId["+taskId+"], times " + tryTime);
				}
				res = HttpUtils.post(url, json);
			} catch (Exception e) {
				logger.warn("upload file, taskId["+taskId+"]. times["+tryTime+"]", e);
				tryTime++;
				if (tryTime >= uploadFaildRetryTimes) {
					throw new WxApiInvorkException(e);
				}
			}
		}
		logger.debug("upload weixin news media complate, taskId[{}], req[{}], res[{}]", taskId, json, res);
		//
		Map<String, Object> obj = JsonUtils.parseJsonMap(res);
		if (obj.get("type") == null) {
			throw new WxApiInvorkException("media upload request faild. taskId["+taskId+"], res["+JsonUtils.toJson(obj)+"], req["+json+"]"); 
		}
		String media_id = (String) (obj.get("media_id") != null ? obj.get("media_id") : obj.get("thumb_media_id"));
		this.setNewsMediaId(media_id);
		logger.info("news media upload complate, taskId[{}], media_id[{}]", taskId, media_id);
		return this;
	}
	

	/**
	 * 预览推送任务的数据
	 * @throws WxApiInvorkException
	 * @throws IOException 
	 */
	public String dataJson_preview(String openid) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("touser", openid);
		data.put("msgtype", "mpnews");
		Map<String, Object> mpnews = new HashMap<String, Object>();
		mpnews.put("media_id", getNewsMediaId());
		data.put("mpnews", mpnews);
		String json = JsonUtils.toJson(data);
		return json;
	}
	
	
	/**
	 * 预览推送
	 * @throws IOException
	 * @throws WxApiInvorkException
	 */
	public PushSubmitResult preview(String openid) throws IOException, WxApiInvorkException {
		// 确保上传
		upload();
		logger.info("preview send media, taskId[{}], media_id[{}], openid[{}]", taskId, getNewsMediaId(), openid);
		//
		String json = dataJson_preview(openid);
		//
		logger.debug("preview data complate, taskId[{}], data[{}]", taskId, json);
		String url = pushPreviewUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
		String res = HttpUtils.post(url, json);
		logger.debug("preview submit complate, taskId[{}], url[{}], req[{}], res[{}]", taskId, url, json, res);
		//
		Map<String, Object> obj = JsonUtils.parseJsonMap(res);
		if (obj.get("errcode") == null || !obj.get("errcode").equals(new Integer(0))) {
			logger.warn("media preview submit faild. taskId["+taskId+"], url["+url+"], res["+JsonUtils.toJson(obj)+"], req["+json+"]");
			return new PushSubmitResult(Integer.parseInt(obj.get("errcode").toString()), (String) obj.get("errmsg"));
		}
		logger.info("media preview submit success. taskId[{}], media_id[{}], openid[{}], msg_id[{}]", 
				taskId, getNewsMediaId(), openid, obj.get("msg_id"));
		return new PushSubmitResult(Integer.parseInt(obj.get("errcode").toString()), (String) obj.get("errmsg"), 
				obj.get("msg_id") == null ? null : Long.parseLong(obj.get("msg_id").toString()));
	}
	

	/**
	 * 提交群发任务的数据
	 * @throws WxApiInvorkException
	 * @throws IOException 
	 */
	public String dataJson_sendAll() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("msgtype", "mpnews");
		Map<String, Object> mpnews = new HashMap<String, Object>();
		mpnews.put("media_id", getNewsMediaId());
		data.put("mpnews", mpnews);
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("is_to_all", true);
		data.put("filter", filter);
		String json = JsonUtils.toJson(data);
		return json;
	}
	
	
	/**
	 * 提交群发任务
	 * @throws WxApiInvorkException
	 * @throws IOException 
	 * 
	 * 2015-10-31 shiying
	 * 出现了微信群发提交后串到另一个微信的问题，
	 * 经日志分析，为AccessToken获取错误，（西安联通）公众帐号群发获取到了（彩拓网络订阅号）的accessToken
	 * 初步判断可能是一下原因
	 * 	1. 多微信支持服务，通过AppId获取到了错误的微信服务管理器（排查位置：WxServiceMultipleManager.getWxServiceManager）
	 *  2. 使用正确的微信服务管理器，获取到了错误的微信服务（排查位置：WxServiceManager.getWxService）
	 *  3. 使用正确的微信服务，但是底层缓存服务冲突，返回了错误的缓存结果（排查位置：WxApiAccessTokenDefaultService.accessToken）
	 *  
	 * 现增加sendAll方法的传入参数（wxAppId），增加对appId的二次判断，
	 * 如果当前wxAppId与通过AccessToken服务获取的不一致，抛出异常。
	 * 
	 */
	public PushSubmitResult sendAll(String wxAppId) throws WxApiInvorkException, IOException {
		// 确保上传
		upload();
		logger.info("send media to all user, taskid[{}], media_id[{}]", taskId, getNewsMediaId());
		if (!wxAppId.equals(wxServiceManager.getWxService(WxApiAccessTokenService.class).appId())) {
			throw new WxApiInvorkException("warning : weixin appid not match. taskId["+taskId+"], service["
						+wxServiceManager.getWxService(WxApiAccessTokenService.class).appId()+"], pramar["+wxAppId+"]");
		}
		//
		String json = dataJson_sendAll();
		//
		logger.debug("send media to all user, taskid[{}], data[{}]", taskId, json);
		String url = sendByGroupUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
		String res = HttpUtils.post(url, json);
		logger.debug("send media to all user, taskid[{}], url[{}], data[{}], res[{}]", taskId, url, json, res);
		//
		Map<String, Object> obj = JsonUtils.parseJsonMap(res);
		if (obj.get("errcode") == null || !obj.get("errcode").equals(new Integer(0))) {
			logger.warn("send media to all user submit faild. taskId[" + taskId + "], res["+JsonUtils.toJson(obj)+"], req["+json+"]");
			return new PushSubmitResult(Integer.parseInt(obj.get("errcode").toString()), (String) obj.get("errmsg"));
		}
		logger.info("media preview submit success. taskId[{}], media_id[{}], msg_id[{}], msg_data_id[{}]", taskId, getNewsMediaId(), obj.get("msg_id"), obj.get("msg_data_id"));
		return new PushSubmitResult(
				(String) obj.get("type"), 
				Integer.parseInt(obj.get("errcode").toString()), (String) obj.get("errmsg"), 
				Long.parseLong(obj.get("msg_id").toString()), Long.parseLong(obj.get("msg_data_id").toString()));
	}
	
	
	/**
	 * 推送提交结果
	 * 
	 * @author shiying
	 *
	 */
	public static class PushSubmitResult {
		String type;
		Integer errcode;
		String errmsg;
		Long msg_id;
		Long msg_data_id;
		public PushSubmitResult(Integer errcode, String errmsg) {
			super();
			this.errcode = errcode;
			this.errmsg = errmsg;
		}
		public PushSubmitResult(Integer errcode, String errmsg, Long msg_id) {
			super();
			this.errcode = errcode;
			this.errmsg = errmsg;
			this.msg_id = msg_id;
		}
		public PushSubmitResult(String type, Integer errcode, String errmsg,
				Long msg_id, Long msg_data_id) {
			super();
			this.type = type;
			this.errcode = errcode;
			this.errmsg = errmsg;
			this.msg_id = msg_id;
			this.msg_data_id = msg_data_id;
		}
		public boolean isSuccess() {
			return errcode != null && errcode == 0;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Integer getErrcode() {
			return errcode;
		}
		public void setErrcode(Integer errcode) {
			this.errcode = errcode;
		}
		public String getErrmsg() {
			return errmsg;
		}
		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
		public Long getMsg_id() {
			return msg_id;
		}
		public void setMsg_id(Long msg_id) {
			this.msg_id = msg_id;
		}
		public Long getMsg_data_id() {
			return msg_data_id;
		}
		public void setMsg_data_id(Long msg_data_id) {
			this.msg_data_id = msg_data_id;
		}
	}
	
	
	/**
	 * 群发图文菜单项
	 * 
	 * @author shiying
	 *
	 */
	private static class WxPushNewsMessageItem {
		// 缩略图地址
		private String thumb_media_id;
		// 标题
		private String title;
		// 描述摘要
		private String digest;
		// 内容正文
		private String content;
		// 是否显示封面，1为显示，0为不显示
		private String show_cover_pic;
		// 图文消息的作者
		private String author;
		// 在图文消息页面点击“阅读原文”后的页面
		private String content_source_url;
		@SuppressWarnings("unused")
		public String getThumb_media_id() {
			return thumb_media_id;
		}
		public void setThumb_media_id(String thumb_media_id) {
			this.thumb_media_id = thumb_media_id;
		}
		@SuppressWarnings("unused")
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		@SuppressWarnings("unused")
		public String getDigest() {
			return digest;
		}
		public void setDigest(String digest) {
			this.digest = digest;
		}
		@SuppressWarnings("unused")
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		@SuppressWarnings("unused")
		public String getShow_cover_pic() {
			return show_cover_pic;
		}
		public void setShow_cover_pic(String show_cover_pic) {
			this.show_cover_pic = show_cover_pic;
		}
		@SuppressWarnings("unused")
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		@SuppressWarnings("unused")
		public String getContent_source_url() {
			return content_source_url;
		}
		public void setContent_source_url(String content_source_url) {
			this.content_source_url = content_source_url;
		}
	}


	public String getNewsMediaUploadUrl() {
		return newsMediaUploadUrl;
	}


	public void setNewsMediaUploadUrl(String newsMediaUploadUrl) {
		this.newsMediaUploadUrl = newsMediaUploadUrl;
	}


	public List<WxPushNewsMessageItem> getItems() {
		return items;
	}


	public void setItems(List<WxPushNewsMessageItem> items) {
		this.items = items;
	}


	public String getNewsMediaId() {
		return newsMediaId;
	}


	public void setNewsMediaId(String newsMediaId) {
		this.newsMediaId = newsMediaId;
	}


	public String getMediaUrlBase() {
		return mediaUrlBase;
	}


	public void setMediaUrlBase(String mediaUrlBase) {
		this.mediaUrlBase = mediaUrlBase;
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


	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}


	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	

}
