package serv.wx.service.manager;

/**
 * 微信服务加载器
 * 
 * @author shiying
 *
 * @param <T>
 */
public interface WxServiceLoader<T extends WxService> {
	
	/**
	 * 加载微信服务
	 * @return
	 */
	public T load(WxServiceManager wxServiceManager);
	
}
