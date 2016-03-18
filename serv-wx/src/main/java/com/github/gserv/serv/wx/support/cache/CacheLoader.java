package com.github.gserv.serv.wx.support.cache;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.service.manager.WxServiceLoader;

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
