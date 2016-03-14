package serv.wx.support.cache;

import serv.wx.service.manager.WxServiceLoader;
import serv.wx.service.manager.WxServiceManager;

public class CacheLoader implements WxServiceLoader<Cache> {
	
	private Cache cache;

	@Override
	public Cache load(WxServiceManager wxServiceManager) {
		if (cache == null) {
			return new CacheImpl();
		}
		return cache;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	
}
