package com.github.gserv.serv.web.filter;

import javax.servlet.Filter;

/**
 * 过滤器包装类，
 * 用来包装由代理处理的过滤器
 * 
 * @author shiying
 *
 */
public class FilterProxyWrap {
	
	// 目标过滤器
	private Filter filter;
	
	// 优先级，数越大优先级越高
	private Integer priority = 1;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	

}
