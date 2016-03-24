package serv.support.CustomerServiceInject;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenDefaultService;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenProxyService;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 测试当向ServiceLoader注入wxApiAccessTokenService的情况
 *
 * 无论数据如何，都会返回用户注入的wxApiAccessTokenService
 *
 * Created by shiying on 2016/3/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:serv/support/CustomerServiceInject/applicationContext-customerService.xml")
public class TestCustomerServiceInject {

    @Resource(name="wxServiceManager_wxapi")
    private WxServiceManager wxServiceManager_wxapi;

    @Resource(name="wxServiceManager_proxy")
    private WxServiceManager wxServiceManager_proxy;

    @Test
    public void test_wxapi() throws WxApiInvorkException {
        System.out.println("--- > " + wxServiceManager_wxapi.getWxService(WxApiAccessTokenService.class));
        Assert.assertTrue(wxServiceManager_wxapi.getWxService(WxApiAccessTokenService.class) instanceof ExampleWxAccessTokenService);
        Assert.assertNotNull(((ExampleWxAccessTokenService) wxServiceManager_wxapi.getWxService(WxApiAccessTokenService.class)).getWxServiceManager());
    }

    @Test
    public void test_proxy() throws WxApiInvorkException {
        System.out.println("--- > " + wxServiceManager_proxy.getWxService(WxApiAccessTokenService.class));
        Assert.assertTrue(wxServiceManager_proxy.getWxService(WxApiAccessTokenService.class) instanceof ExampleWxAccessTokenService);
        Assert.assertNotNull(((ExampleWxAccessTokenService) wxServiceManager_wxapi.getWxService(WxApiAccessTokenService.class)).getWxServiceManager());
    }

}
