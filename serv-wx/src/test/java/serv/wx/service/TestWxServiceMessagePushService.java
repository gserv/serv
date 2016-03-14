package serv.wx.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.wx.message.send.SendTextMessage;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.service.WxServiceMessagePushService;

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
