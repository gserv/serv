package com.github.gserv.serv.wx.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.api.media.WxMediaService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.commons.ResourcesUtils;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.media.MediaFileType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestWxMediaService {
	
//	@Resource
	private WxMediaService wxMediaService;

	@Resource
	private WxServiceManager wxServiceManager;


	private File getTmpFile(String name) {
		wxMediaService = wxServiceManager.getWxService(WxMediaService.class);
		URL url = this.getClass().getClassLoader().getResource("static/" + name);
		File file = null;
		try {
			file = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return file;
	}
	

	@Test
	public void testImage() throws IOException, WxApiInvorkException {
		wxMediaService = wxServiceManager.getWxService(WxMediaService.class);
		File tmp = getTmpFile("thumb.jpg");
		String media_id = wxMediaService.upload(
				MediaFileType.image, tmp);
		System.out.println(media_id);
		Assert.assertNotNull(media_id);
	}

	@Test
	public void testVideo() throws WxApiInvorkException, IOException {
		wxMediaService = wxServiceManager.getWxService(WxMediaService.class);
		//
		String voiceLocalUrl = "classpath://static/video_mp4_h264.mp4";
//		String voiceLocalUrl = "classpath://static/system.mp3";
//		String voiceLocalUrl = "classpath://static/test_image.jpg";
		InputStream input = ResourcesUtils.loadResourcesByUrl(voiceLocalUrl);
		System.out.println(voiceLocalUrl + " : " + input);
		String separ = voiceLocalUrl.startsWith("file://") ? File.separator : "/";
		String filename = voiceLocalUrl.substring(voiceLocalUrl.lastIndexOf(separ) + separ.length(), voiceLocalUrl.length());
		String mediaId = wxMediaService.upload(MediaFileType.video, input, filename);
		System.out.println("mediaId >> " + mediaId);
		//
		System.out.println("getMediaUrl >> " + wxMediaService.getMediaUrl(mediaId));
		System.out.println("getMediaFile >> " + wxMediaService.getMediaFile(mediaId));
	}

	@Test
	public void test() throws WxApiInvorkException, IOException {
		wxMediaService = wxServiceManager.getWxService(WxMediaService.class);
		//
		File tmp = getTmpFile("thumb.jpg");
		String media_id = wxMediaService.upload(MediaFileType.image, tmp);
		System.out.println("media_id >> " + media_id);
		File media_file = wxMediaService.getMediaFile(media_id);
		System.out.println("media_file >> " + media_file);
	}

}
