package com.github.gserv.serv.wx.service;

import java.util.Date;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.message.send.SendTextMessage;
import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.service.WxServiceMessagePushService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestWxServiceMessagePushService {
	
//	@Resource
	private WxServiceMessagePushService wxServiceMessagePushService;

	@Resource
	private WxServiceManager wxServiceManager;
	
	@Test
	public void test() throws WxApiInvorkException {
		wxServiceMessagePushService = wxServiceManager.getWxService(WxServiceMessagePushService.class);
		SendTextMessage sendMessage = new SendTextMessage("oRVwHuIA8N8MaHb_12K3HCNmOs9E", null, new Date(), "hello");
//		wxServiceMessagePushService.sendMessage(sendMessage);
	}

}
