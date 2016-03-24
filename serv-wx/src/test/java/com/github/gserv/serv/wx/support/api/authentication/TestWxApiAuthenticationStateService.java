package com.github.gserv.serv.wx.support.api.authentication;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestWxApiAuthenticationStateService {
	
	@Resource
	private WxServiceManager wxServiceManager;
	
	@Test
	public void test_isAuthentication() throws WxApiInvorkException {
//		WxApiAuthenticationStateService wxApiAuthenticationStateService = wxServiceManager.getWxService(WxApiAuthenticationStateService.class);
//		boolean val = wxApiAuthenticationStateService.isAuthentication();
//		System.out.println(val);
	}

}
