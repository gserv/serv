package serv.wx.support;

/**
 * 微信通知调用上下文
 * 
 * @author shiying
 *
 */
public class WxNoticeContext {
	
	/**
	 * 当前Http服务的BasePath
	 */
	String serverBasePath;

	public String getServerBasePath() {
		return serverBasePath;
	}

	public void setServerBasePath(String serverBasePath) {
		this.serverBasePath = serverBasePath;
	}

	

}
