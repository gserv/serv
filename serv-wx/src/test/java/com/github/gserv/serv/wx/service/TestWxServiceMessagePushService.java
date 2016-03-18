package com.github.gserv.serv.wx.service;

import java.util.Date;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.message.send.SendTextMessage;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.service.WxServiceMessagePushService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx.xml")
public class TestWxServiceMessagePushService {
	
	@Resource
	private WxServiceMessagePushService wxServiceMessagePushService;
	
	@Test
	public void test() throws WxApiInvorkException {
		SendTextMessage sendMessage = new SendTextMessage("oRVwHuIA8N8MaHb_12K3HCNmOs9E", null, new Date(), "hello");
		wxServiceMessagePushService.sendMessage(sendMessage);
	}

}
