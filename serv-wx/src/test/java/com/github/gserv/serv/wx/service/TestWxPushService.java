package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.support.api.push.WxPushService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx.xml")
public class TestWxPushService {
	
	@Resource
	private WxPushService wxPushService;
	
	public void test() {
//		WxPushNewsMessage wxPushNewsMessage = wxPushService.createWxNewsMessagePush();
		
		
	}

}
