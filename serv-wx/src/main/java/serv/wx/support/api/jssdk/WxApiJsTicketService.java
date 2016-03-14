package serv.wx.support.api.jssdk;

import serv.wx.service.manager.WxService;
import serv.wx.support.WxApiInvorkException;

/**
 * 微信API接口，获取jsTicket
 * 
 * @author shiying
 *
 */
public interface WxApiJsTicketService extends WxService {
	
	/**
	 * 获取jsTicket
	 * @param refresh 强制刷新（不建议）
	 * @return
	 * @throws WxApiInvorkException
	 */
	public String jsTicket(boolean refresh) throws WxApiInvorkException;
	
	/**
	 * 获取jsTicket
	 * @return
	 * @throws WxApiInvorkException
	 */
	public String jsTicket() throws WxApiInvorkException;
	
}
