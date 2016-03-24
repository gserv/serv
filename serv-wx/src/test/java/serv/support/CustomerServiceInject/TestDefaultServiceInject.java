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
 * 测试服务获取的默认逻辑（未向ServiceLoader注入wxApiAccessTokenService时）
 * 根据注入的属性进行区分返回的service实现
 *
 * Created by shiying on 2016/3/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:serv/support/CustomerServiceInject/applicationContext-default.xml")
public class TestDefaultServiceInject {

    @Resource(name="wxServiceManager_wxapi")
    private WxServiceManager wxServiceManager_wxapi;

    @Resource(name="wxServiceManager_proxy")
    private WxServiceManager wxServiceManager_proxy;

    @Test
    public void test_wxapi() throws WxApiInvorkException {
        System.out.println("--- > " + wxServiceManager_wxapi.getWxService(WxApiAccessTokenService.class));
        Assert.assertTrue(wxServiceManager_wxapi.getWxService(WxApiAccessTokenService.class) instanceof WxApiAccessTokenDefaultService);
    }

    @Test
    public void test_proxy() throws WxApiInvorkException {
        System.out.println("--- > " + wxServiceManager_proxy.getWxService(WxApiAccessTokenService.class));
        Assert.assertTrue(wxServiceManager_proxy.getWxService(WxApiAccessTokenService.class) instanceof WxApiAccessTokenProxyService);
    }

}
