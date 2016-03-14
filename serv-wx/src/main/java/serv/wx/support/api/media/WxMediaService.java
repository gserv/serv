package serv.wx.support.api.media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.FileUtils;
import serv.commons.HttpUtils;
import serv.commons.JsonUtils;
import serv.commons.encry.HashUtils;
import serv.wx.service.manager.WxService;
import serv.wx.service.manager.WxServiceManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import serv.wx.support.cache.Cache;

/**
 * 微信媒体服务
 * 
 * @author shiying
 *
 */
public class WxMediaService implements WxService {
	private static final Logger logger = LoggerFactory.getLogger(WxMediaService.class);
	
	/**
	 * 媒体上传URL
	 */
	private String mediaUploadUrl = "https://api.weixin.qq.com/cgi-bin/media/upload";
	
	/**
	 * 媒体获取URL
	 */
	private String mediaGetUrl = "https://api.weixin.qq.com/cgi-bin/media/get";
	
	/**
	 * 服务管理器
	 */
	private WxServiceManager wxServiceManager;
	
	
	/**
	 * 通过MediaId下载
	 * @param mediaId
	 * @return
	 * @throws WxApiInvorkException
	 * @throws IOException
	 */
	public File getMediaFile(String mediaId) throws WxApiInvorkException, IOException {
		File file = HttpUtils.download(getMediaUrl(mediaId));
		return file;
	}
	
	
	/**
	 * 获取媒体URL地址
	 * @param mediaId
	 * @return
	 * @throws WxApiInvorkException
	 */
	public String getMediaUrl(String mediaId) throws WxApiInvorkException {
		return mediaGetUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken() + "&media_id=" + mediaId;
	}
	
	
	/**
	 *  通过URL地址转储上传
	 */
	public String upload(MediaFileType type, String fileurl) throws IOException, WxApiInvorkException {
		return upload(type, fileurl, false);
	}
	
	
	/**
	 *  通过URL地址转储上传
	 */
	public String upload(MediaFileType type, String fileurl, boolean compulsoryUpload) throws IOException, WxApiInvorkException {
		 URL realUrl = new URL(fileurl);
         URLConnection connection = realUrl.openConnection();
         connection.setRequestProperty("accept", "*/*");
         connection.setRequestProperty("connection", "Keep-Alive");
         connection.setRequestProperty("user-agent",
                 "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
         connection.connect();
         InputStream is = connection.getInputStream();
         try {
        	 return upload(type, is, fileurl.substring(fileurl.lastIndexOf("/"), fileurl.length()), compulsoryUpload);
        	 
         } finally {
        	 is.close();
         }
	}
	

	public String upload(MediaFileType type, InputStream input, String filename) throws IOException, WxApiInvorkException {
		return upload(type, input, filename, false);
	}
	
	
	/**
	 * 上传资源流
	 * @param type
	 * @param input
	 * @return
	 * @throws IOException
	 * @throws WxApiInvorkException
	 */
	public String upload(MediaFileType type, InputStream input, String filename, boolean compulsoryUpload) throws IOException, WxApiInvorkException {
		File tmpFile = FileUtils.createTempFile(filename);
		tmpFile.getParentFile().mkdirs();
		tmpFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(tmpFile);
		try {
			byte[] bytes = new byte[2000];
			int readsize = -1;
			while (( readsize = input.read(bytes)) > 0) {
				fos.write(bytes, 0, readsize);
			}
		} finally {
			fos.flush();
			fos.close();
			input.close();
		}
		tmpFile.deleteOnExit();
		return upload(type, tmpFile, compulsoryUpload);
	}
	
	/**
	 * 文件上传
	 * @param type
	 * @param upload 文件
	 * @return media_id
	 * @throws IOException
	 * @throws WxApiInvorkException
	 */
	public String upload(MediaFileType type, File upload) throws IOException, WxApiInvorkException {
		return upload(type, upload, false);
	}
	
	/**
	 * 文件上传
	 * @param type
	 * @param upload 文件
	 * @return media_id
	 * @return compulsoryUpload 强制上传，跳过缓存
	 * @throws IOException
	 * @throws WxApiInvorkException
	 */
	public String upload(MediaFileType type, File upload, boolean compulsoryUpload) throws IOException, WxApiInvorkException {
		String cacheKey = "media_" + wxServiceManager.getAppId() + "_" + HashUtils.md5(upload);
		String cacheMediaId = wxServiceManager.getWxService(Cache.class).get(cacheKey);
		if (!compulsoryUpload && cacheMediaId != null) {
			return cacheMediaId;
		}
		//
		String url = mediaUploadUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken() + "&type=" + type.toString();
		logger.debug("start media upload. type[{}], upload[{}], url[{}]", type, upload, url);
		Map<String, File> parts = new HashMap<String, File>();
		parts.put("media", upload);
		String json = HttpUtils.post(url, parts, new HashMap<String, String>());
		logger.debug("upload media complate. type[{}], upload[{}], response[{}]", type, upload, json);
		Map<String, Object> obj = JsonUtils.parseJsonMap(json);
		if (obj.get("type") == null) {
			throw new WxApiInvorkException("media upload request faild. res ["+JsonUtils.toJson(obj)+"], type["+type+"], file["+upload+"]"); 
		}
		String media_id = (String) (obj.get("media_id") != null ? obj.get("media_id") : obj.get("thumb_media_id"));
		//
		if (media_id != null) {
			wxServiceManager.getWxService(Cache.class).set(cacheKey, media_id, 60*10);
		}
		return media_id;
	}

	public void setMediaUploadUrl(String mediaUploadUrl) {
		this.mediaUploadUrl = mediaUploadUrl;
	}


	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}


	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	
}
