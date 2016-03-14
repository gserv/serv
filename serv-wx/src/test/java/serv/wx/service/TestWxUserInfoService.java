package serv.wx.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.commons.JsonUtils;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.userinfo.WeixinUser;
import serv.wx.support.api.userinfo.WxUserInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:serv/applicationContext-wx.xml")
public class TestWxUserInfoService {
	
	@Resource
	private WxUserInfoService wxUserInfoService;
	
	@Test
	public void test() throws WxApiInvorkException {
		List<WeixinUser> user = wxUserInfoService.getUserInfo(new HashSet<String>(Arrays.asList(new String[] {"oRVwHuIA8N8MaHb_12K3HCNmOs9E"})));
		System.out.println(JsonUtils.toJson(user));
	}

}
