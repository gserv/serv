package com.github.gserv.serv.wx.service;

import com.github.gserv.serv.wx.service.multiple.WxServiceMultipleManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.wx.conf.WxConf;
import com.github.gserv.serv.wx.service.multiple.WxServiceMultipleSupport;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/serv/applicationContext-wx-server.xml", "/serv/applicationContext-springfactory.xml"})
public class TestWxServiceMultipleManager {
	
//	@Resource
	private WxServiceMultipleManager wxServiceMultipleManager = new WxServiceMultipleManager();
	
	@Test
	public void test() throws WxApiInvorkException {
		wxServiceMultipleManager.setWxServiceMultipleSupport(new WxServiceMultipleSupport() {
			
			@Override
			public WxConf getWxConfByAppid(String appId) {
				return new WxConf() {

					@Override
					public String getAppId() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getAppSecret() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getToken() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getEncodingAESKey() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String getProxyAccessTokenUrl() {
						return "http://123.139.154.144/wxapi_proxy/api/accessToken.do";
					}

					@Override
					public String getProxyAccessTokenAccessId() {
						return "wx_test_shiying";
					}

					@Override
					public String getProxyAccessTokenAccessKey() {
						return "test";
					}
					
				};
			}
		});
		System.out.println(wxServiceMultipleManager.getWxServiceManager(""));
		System.out.println(wxServiceMultipleManager.getWxServiceManager("").getWxService(WxApiAccessTokenService.class));
		String accessToken = wxServiceMultipleManager.getWxServiceManager("").getWxService(WxApiAccessTokenService.class).accessToken();
		System.out.println(accessToken);
	}

}
