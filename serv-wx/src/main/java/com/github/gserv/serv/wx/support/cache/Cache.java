package com.github.gserv.serv.wx.support.cache;

import java.io.Serializable;
import java.util.Set;

import com.github.gserv.serv.wx.service.manager.WxService;

/**
 * 缓存
 * 
 * @author shiying
 *
 */
public interface Cache extends WxService {
	
	/**
	 * 设置
	 * @param key
	 * @param serial
	 * @param timeout
	 */
	public <T extends Serializable> void set(String key, Serializable serial, Integer timeoutSecond);
	
	/**
	 * 设置
	 * @param key
	 * @param serial
	 */
	public <T extends Serializable> void set(String key, Serializable serial);
	
	/**
	 * 获取
	 * @param key
	 * @return
	 */
	public <T extends Serializable> T get(String key);
	
	/**
	 * 删除
	 * @param key
	 */
	public void remove(String key);
	
	/**
	 * 获取Key列表
	 * @return
	 */
	public Set<String> keys();
	
	

}






