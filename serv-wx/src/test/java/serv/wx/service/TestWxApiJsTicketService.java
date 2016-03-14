package serv.wx.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.jssdk.WxApiJsTicketService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx.xml")
public class TestWxApiJsTicketService {
	
	@Resource
	private WxApiJsTicketService wxApiJsTicketService;
	
	@Test
	public void test() throws WxApiInvorkException {
		System.out.println(wxApiJsTicketService.jsTicket());
		System.out.println(wxApiJsTicketService.jsTicket());
	}

}
