package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.support.api.jssdk.WxApiJsSdkConfService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.jssdk.JsSdkConf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx.xml")
public class TestWxApiJsSdkConfService {
	
	@Resource
	private WxApiJsSdkConfService wxApiJsSdkConfService;
	
	@Test
	public void test() throws WxApiInvorkException {
		JsSdkConf conf = wxApiJsSdkConfService.generatorConf("http://www.baidu.com");
		System.out.println(JsonUtils.toJson(conf));
	}

}
