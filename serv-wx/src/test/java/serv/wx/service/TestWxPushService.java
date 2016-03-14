package serv.wx.service;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.wx.support.api.push.WxPushNewsMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx.xml")
public class TestWxPushService {
	
	@Resource
	private serv.wx.support.api.push.WxPushService wxPushService;
	
	public void test() {
//		WxPushNewsMessage wxPushNewsMessage = wxPushService.createWxNewsMessagePush();
		
		
	}

}
