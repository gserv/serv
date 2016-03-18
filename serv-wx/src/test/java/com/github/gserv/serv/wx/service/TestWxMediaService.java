package com.github.gserv.serv.wx.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.support.api.media.WxMediaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.commons.ResourcesUtils;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.media.MediaFileType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx-server.xml")
public class TestWxMediaService {
	
	@Resource
	private WxMediaService wxMediaService;
	

//	@Test
	public void testImage() throws IOException, WxApiInvorkException {
		String media_id = wxMediaService.upload(
				MediaFileType.image, new File("C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\20150828102534482_69454_\\133210071_109.jpg"));
		System.out.println(media_id);
	}

//	@Test
	public void testVideo() throws WxApiInvorkException, IOException {
		String voiceLocalUrl = "classpath://sy/serv/wx/video_mp4_h264.mp4";
//		String voiceLocalUrl = "classpath://sy/serv/wx/system.mp3";
//		String voiceLocalUrl = "classpath://sy/serv/wx/test_image.jpg";
		InputStream input = ResourcesUtils.loadResourcesByUrl(voiceLocalUrl);
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
		String media_id = wxMediaService.upload(MediaFileType.image, new File("d://d1.jpg"));
		System.out.println("media_id >> " + media_id);
		File media_file = wxMediaService.getMediaFile(media_id);
		System.out.println("media_file >> " + media_file);
	}

}
