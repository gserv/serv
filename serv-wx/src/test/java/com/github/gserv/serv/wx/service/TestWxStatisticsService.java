package com.github.gserv.serv.wx.service;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.multiple.WxServiceMultipleManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.statistics.WxStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext-test.xml")
public class TestWxStatisticsService {
	
	@Resource
	private WxServiceMultipleManager wxServiceMultipleManager;
	
	@Test
	public void test() throws WxApiInvorkException, IOException {
		wxServiceMultipleManager.getWxServiceManager("wx80567651b493e9cf").getWxService(WxStatisticsService.class)
			.getarticletotal(new Date(new Date().getTime()-1000*60*60*24*1));
	}

}
