package com.github.gserv.serv.wx.support.api.statistics;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.commons.JsonUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/serv/_applicationContext-wx-server-single.xml")
public class TestWxStatisticsService {
	
	@Resource
	private WxServiceManager wxServiceManager;
	
	@Test
	public void test_getarticletotal() throws WxApiInvorkException, IOException {
		List<StatisticsNewsItem> list = 
				wxServiceManager.getWxService(WxStatisticsService.class).getarticletotal(new Date(new Date().getTime() - 1*1000*60*60*24));
		for (StatisticsNewsItem item : list) {
			System.out.println(JsonUtils.toJson(item));
		}
	}
	
//	@Test
	public void test_getusercumulate() throws WxApiInvorkException, IOException {
		List<StatisticsUserCumulate> list = 
				wxServiceManager.getWxService(WxStatisticsService.class).getusercumulate(
						new Date(new Date().getTime() - 1*1000*60*60*24), new Date(new Date().getTime() - 1000*60*60*24));
		for (StatisticsUserCumulate item : list) {
			System.out.println(JsonUtils.toJson(item));
		}
	}
	
	
}
