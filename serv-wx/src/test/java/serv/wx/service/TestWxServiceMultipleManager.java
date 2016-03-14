package serv.wx.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.wx.conf.WxConf;
import serv.wx.service.multiple.WxServiceMultipleSupport;
import serv.wx.support.WxApiInvorkException;
import serv.wx.support.api.accesstoken.WxApiAccessTokenService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/serv/applicationContext-wx-server.xml", "/serv/applicationContext-springfactory.xml"})
public class TestWxServiceMultipleManager {
	
//	@Resource
	private serv.wx.service.multiple.WxServiceMultipleManager wxServiceMultipleManager = new serv.wx.service.multiple.WxServiceMultipleManager();
	
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
