package serv.wx.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.wx.service.manager.WxServiceManager;
import serv.wx.service.multiple.WxServiceMultipleManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import serv.wx.support.cache.Cache;

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
