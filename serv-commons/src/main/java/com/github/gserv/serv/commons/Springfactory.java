package com.github.gserv.serv.commons;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class Springfactory implements BeanFactoryAware {

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
			return (T) beanFactory.getBean(beanName);
		}
		return null;
	}

}





