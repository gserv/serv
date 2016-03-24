package com.github.gserv.serv.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import javax.annotation.PostConstruct;

public class Springfactory implements BeanFactoryAware {
	private static final Logger logger = LoggerFactory.getLogger(Springfactory.class);

	private static BeanFactory beanFactory;

	// private static ApplicationContext context;

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		Springfactory.beanFactory = factory;
	}
	
	/**
	 * 根据类型获得Bean
	 * @param t
	 * @return bean
	 */
	public static <T> T getBean(Class<T> t) {
		if (null != beanFactory) {
			return (T) beanFactory.getBean(t);
		}
		logger.warn("try get a spring bean by springfactory, but beanFactory is null, maybe not config Springfactory to applicationContext.");
		return null;
	}

	/**
	 * 根据beanName名字取得bean
	 * 
	 * @param beanName
	 * @return bean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if (null != beanFactory) {
			logger.warn("try get a spring bean by springfactory, but beanFactory is null, maybe not config Springfactory to applicationContext.");
			return (T) beanFactory.getBean(beanName);
		}
		return null;
	}

}





