package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.commons.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestWxServiceManager {
	
	@Resource
	private WxServiceManager wxServiceManager;
	
	@Test
	public void test() throws WxApiInvorkException {
		System.out.println(JsonUtils.toJson(wxServiceManager));
	}

}
