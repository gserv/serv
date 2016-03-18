package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.service.multiple.WxServiceMultipleManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.cache.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-test.xml")
public class TestWxMultipleService {
	
	@Resource
	private WxServiceMultipleManager wxServiceMultipleManager;
	
	@Test
	public void test_getWxApiAccessTokenService() throws WxApiInvorkException {
		String wxAppId = "wx5641c2474b7e5ab7";
		WxServiceManager wxServiceManager = wxServiceMultipleManager.getWxServiceManager(wxAppId);
		Cache cache = wxServiceManager.getWxService(Cache.class);
		System.out.println(cache);
	}

}
