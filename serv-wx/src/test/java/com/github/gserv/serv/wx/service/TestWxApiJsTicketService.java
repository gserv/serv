package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.jssdk.WxApiJsTicketService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestWxApiJsTicketService {
	
//	@Resource
	private WxApiJsTicketService wxApiJsTicketService;

	@Resource
	private WxServiceManager wxServiceManager;
	
	@Test
	public void test() throws WxApiInvorkException {
		wxApiJsTicketService = wxServiceManager.getWxService(WxApiJsTicketService.class);
		System.out.println(wxApiJsTicketService.jsTicket());
		System.out.println(wxApiJsTicketService.jsTicket());
	}

}
