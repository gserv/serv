package serv.wx.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx.xml")
public class TestWxApiAccessTokenService {
	
	@Resource
	private WxApiAccessTokenService wxApiAccessTokenService;
	
	@Test
	public void test() throws WxApiInvorkException {
		System.out.println(wxApiAccessTokenService.accessToken());
		System.out.println(wxApiAccessTokenService.accessToken());
	}

}
