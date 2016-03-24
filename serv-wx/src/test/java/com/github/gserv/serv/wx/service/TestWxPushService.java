package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.api.push.WxPushNewsMessage;
import com.github.gserv.serv.wx.support.api.push.WxPushService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestWxPushService {
	
//	@Resource
	private WxPushService wxPushService;

	@Resource
	private WxServiceManager wxServiceManager;

	@Test
	public void test() {
		wxPushService = wxServiceManager.getWxService(WxPushService.class);
//		WxPushNewsMessage wxPushNewsMessage = wxPushService.createWxNewsMessagePush();
//		wxPushNewsMessage.addNewsItem("")
		
	}

}
