package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.qrcode.WxQrCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext-test.xml")
public class TestWxQrCodeService {
	
	@Resource
	private WxQrCodeService wxQrCodeService;
	
	@Test
	public void test() throws WxApiInvorkException {
		System.out.println(wxQrCodeService.getTempQrCodeUrl(123));
		System.out.println(wxQrCodeService.getForeverQrCodeUrl("test"));
	}

}
