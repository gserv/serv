package com.github.gserv.serv.wx.support.api.accesstoken;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.authentication.WxApiAuthenticationStateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by shiying on 2016/3/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/support/api/accesstoken/applicationContext.xml")
public class TestAccessTokenService {

    @Resource
    private WxServiceManager wxServiceManager;

    @Test
    public void test_isAuthentication() throws WxApiInvorkException {
        WxApiAccessTokenService service = wxServiceManager.getWxService(WxApiAccessTokenService.class);
        System.out.println(service.accessToken());
    }
}
