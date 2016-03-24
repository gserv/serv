package com.github.gserv.serv.wx.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.api.userinfo.WeixinUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.userinfo.WxUserInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestWxUserInfoService {
	
//	@Resource
	private WxUserInfoService wxUserInfoService;

	@Resource
	private WxServiceManager wxServiceManager;
	
	@Test
	public void test() throws WxApiInvorkException {
		wxUserInfoService = wxServiceManager.getWxService(WxUserInfoService.class);
		List<WeixinUser> user = wxUserInfoService.getUserInfo(new HashSet<String>(Arrays.asList(new String[] {"oRVwHuIA8N8MaHb_12K3HCNmOs9E"})));
		System.out.println(JsonUtils.toJson(user));
	}

}
