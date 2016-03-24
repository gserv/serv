package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.wx.support.WxApiInvorkException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestWxApiAccessTokenService {
	
//	@Resource
	private WxApiAccessTokenService wxApiAccessTokenService;

	@Resource
	private WxServiceManager wxServiceManager;
	
	@Test
	public void test() throws WxApiInvorkException {
		wxApiAccessTokenService = wxServiceManager.getWxService(WxApiAccessTokenService.class);
		System.out.println(wxApiAccessTokenService.accessToken());
		System.out.println(wxApiAccessTokenService.accessToken());
	}

}
