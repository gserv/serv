package serv.support.CustomerServiceInject;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.WxApiInvorkException;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;

/**
 * Created by shiying on 2016/3/24.
 */
public class ExampleWxAccessTokenService implements WxApiAccessTokenService {


    private WxServiceManager wxServiceManager;

    @Override
    public String accessToken(boolean refresh) throws WxApiInvorkException {
        return null;
    }

    @Override
    public String accessToken() throws WxApiInvorkException {
        return null;
    }

    @Override
    public String appId() throws WxApiInvorkException {
        return null;
    }

    public WxServiceManager getWxServiceManager() {
        return wxServiceManager;
    }

    public void setWxServiceManager(WxServiceManager wxServiceManager) {
        this.wxServiceManager = wxServiceManager;
    }
}
