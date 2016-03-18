package com.github.gserv.serv.wx.support.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.JsonUtils;

/***
 * 基于内存Hash表的缓存实现类
 * 
 * @author shiying
 *
 */
public class CacheImpl implements Cache {
	private static final Logger logger = LoggerFactory.getLogger(CacheImpl.class);
	
	private Map<String, CacheData> data = new HashMap<String, CacheData>();
	
	/**
	 * 缓存数据
	 * @author shiying
	 *
	 */
	private static class CacheData {
		private Date clearTime;	
		private Serializable data;
		public Date getClearTime() {
			return clearTime;
		}
		public void setClearTime(Date clearTime) {
			this.clearTime = clearTime;
		}
		public Serializable getData() {
			return data;
		}
		public void setData(Serializable data) {
			this.data = data;
		}
	}
	
	/**
	 * 清理
	 */
	private void clear() {
		List<String> clears = new ArrayList<String>();
		for (String key : data.keySet()) {
			if (data.get(key).getClearTime() != null 
					&& data.get(key).getClearTime().after(new Date())) {
				clears.add(key);
			}
		}
		for (String key : clears) {
			data.remove(key);
		}
	}

	@Override
	public <T extends Serializable> void set(String key, Serializable serial,
			Integer timeoutSecond) {
		CacheData cd = new CacheData();
		cd.setClearTime(timeoutSecond == null ? null : new Date(new Date().getTime() + timeoutSecond * 1000));
		cd.setData(serial);
		logger.debug("set cache, key[{}], value[{}]", key, JsonUtils.toJson(cd));
		data.put(key, cd);
	}

	@Override
	public <T extends Serializable> void set(String key, Serializable serial) {
		CacheData cd = new CacheData();
		cd.setClearTime(null);
		cd.setData(serial);
		logger.debug("set cache, key[{}], value[{}]", key, JsonUtils.toJson(cd));
		data.put(key, cd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Serializable> T get(String key) {
		if (data.get(key) != null 
				&& data.get(key).getClearTime() != null 
				&& data.get(key).getClearTime().before(new Date())) {
			data.remove(key);
			logger.debug("remove cache, key[{}]", key);
			return null;
		}
		if (data.get(key) == null || data.get(key).getData() == null) {
			logger.debug("no hit cache, key[{}]", key);
			return null;
		}
		logger.debug("hit cache, key[{}], data[{}]", key, JsonUtils.toJson(data.get(key).getData()));
		return (T) data.get(key).getData();
	}

	@Override
	public void remove(String key) {
		data.remove(key);
	}

	@Override
	public Set<String> keys() {
		clear();
		return data.keySet();
	}

}
