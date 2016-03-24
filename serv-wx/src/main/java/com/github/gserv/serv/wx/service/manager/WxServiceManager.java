package com.github.gserv.serv.wx.service.manager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.github.gserv.serv.commons.scan.ClassScaner;
import com.github.gserv.serv.wx.conf.WxConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.Springfactory;

/**
 * 微信服务管理器
 * 提供对一个微信公众帐号的统一管理
 * 
 * @author shiying
 *
 */
public abstract class WxServiceManager implements WxConf {
	private static final Logger logger = LoggerFactory.getLogger(WxServiceManager.class);
	
	
	/**
	 * 服务描述信息
	 */
	private static Map<Class<? extends WxService>, Class<WxServiceLoader<?>>> serviceMeta;
	
	
	/**
	 * 服务缓存
	 */
	private Map<Class<? extends WxService>, WxService> serviceCache = new HashMap<Class<? extends WxService>, WxService>();
	
	
	/**
	 * 微信基础服务包路径
	 * 此路径下的Service将会被扫描
	 */
	private String wxServiceBaseClassPath = "com.github.gserv.serv.wx";
	
	
	/**
	 * 初始化扫描服务包
	 */
	@SuppressWarnings({ "unchecked" })
	@PostConstruct
	public void init() {
		try {
			if (serviceMeta == null) {
				serviceMeta = new HashMap<Class<? extends WxService>, Class<WxServiceLoader<?>>>();
				//
				new ClassScaner().scan(wxServiceBaseClassPath, new ClassScaner.ClassScanerAction<WxServiceLoader>() {
					@Override
					public void action(Class<WxServiceLoader> cls) {
						try {
							//
							Method method = cls.getMethod("load", WxServiceManager.class);
							serviceMeta.put((Class<? extends WxService>) method.getReturnType(), (Class) cls);
							logger.debug("find wx service, type[{}], server loader [{}]", method.getReturnType(), cls);
						} catch (Exception e) {
							logger.warn("scan treatment class faild. class [{}]", cls);
						}
					}
				}, true, WxServiceLoader.class);
			}
		} catch (IOException e) {
			logger.warn("wx service manager init faild.", e);
		} 
	}
	
	
	/**
	 * 加载服务
	 * @param wxServiceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends WxService> T getWxService(Class<T> wxServiceClass) {
		init();
		T t = (T) serviceCache.get(wxServiceClass);
		if (t != null) {
			logger.debug("hit wx service, appId[{}], wxServiceClass[{}], t[{}]", this.getAppId(), wxServiceClass.getName(), t.toString());
			return t;
		} else if (serviceMeta.get(wxServiceClass) == null) {
			return null;
		} else {
			try {
				Class<? extends WxServiceLoader<?>> wxServiceLoaderClass = serviceMeta.get(wxServiceClass);
				WxServiceLoader<?> wxServiceLoader = Springfactory.getBean(wxServiceLoaderClass);
				if (wxServiceLoader == null) {
					logger.warn("wx service loader load faild, not found in spring factory. class [{}]", wxServiceLoaderClass);
					return null;
				}
				T newT = (T) wxServiceLoader.load(this);
				logger.debug("load wx service, appId[{}], wxServiceClass[{}], newT[{}]", this.getAppId(), wxServiceClass.getName(), newT.toString());
				serviceCache.put(wxServiceClass, newT);
				return newT;
			} catch (Exception e) {
				logger.warn("init wx server faild. type["+wxServiceClass+"], server loader ["+serviceMeta.get(wxServiceClass)+"]", e);
				return null;
			} 
		}
	}
	

}
