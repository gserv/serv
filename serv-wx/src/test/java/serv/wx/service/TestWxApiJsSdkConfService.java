package serv.wx.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.commons.JsonUtils;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.jssdk.JsSdkConf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx.xml")
public class TestWxApiJsSdkConfService {
	
	@Resource
	private serv.wx.support.api.jssdk.WxApiJsSdkConfService wxApiJsSdkConfService;
	
	@Test
	public void test() throws WxApiInvorkException {
		JsSdkConf conf = wxApiJsSdkConfService.generatorConf("http://www.baidu.com");
		System.out.println(JsonUtils.toJson(conf));
	}

}
