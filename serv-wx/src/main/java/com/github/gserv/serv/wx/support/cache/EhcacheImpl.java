package com.github.gserv.serv.wx.support.cache;

import java.io.Serializable;
import java.util.Set;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 基于Ehcache的缓存管理器
 * 
 * @author shiying
 *
 */
public class EhcacheImpl implements Cache {
	
	/**
	 * Ehcache 缓存管理器
	 */
    private CacheManager cacheManager;
    
    /**
     * 缓存名称
     */
    private String cacheName;

	@Override
	public <T extends Serializable> void set(String key, Serializable serial,
			Integer timeoutSecond) {
		Element element = new Element(key, serial);
		element.setTimeToIdle(timeoutSecond);
		element.setTimeToLive(timeoutSecond);
		cacheManager.getCache(cacheName).put(element);
	}

	@Override
	public <T extends Serializable> void set(String key, Serializable serial) {
		Element element = new Element(key, serial);
		cacheManager.getCache(cacheName).put(element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T get(String key) {
		Element element = cacheManager.getCache(cacheName).get(key);
		if (element == null) {
			return null;
		}
		return (T) element.getObjectValue();
	}

	@Override
	public void remove(String key) {
		cacheManager.getCache(cacheName).remove(key);
	}

	@Override
	public Set<String> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	
	

}
