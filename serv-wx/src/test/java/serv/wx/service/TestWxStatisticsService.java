package serv.wx.service;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.wx.service.manager.WxServiceManager;
import serv.wx.service.multiple.WxServiceMultipleManager;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenProxyService;
import serv.wx.support.api.statistics.WxStatisticsService;

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
