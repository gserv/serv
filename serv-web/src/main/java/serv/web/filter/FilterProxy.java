package serv.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 过滤器代理，帮助加载过滤器，
 * 避免需要在web.xml编写大量过滤器
 * 
 * @author shiying
 *
 */
public class FilterProxy implements Filter, FilterChain, ApplicationListener<ContextRefreshedEvent> {
	private static final Logger logger = LoggerFactory.getLogger(FilterProxy.class);
	
	/**
	 * ServletApi 提供的过滤器链对象
	 */
	private static final ThreadLocal<FilterChain> servletApiFilterChain = new ThreadLocal<FilterChain>();
	
	/**
	 * 在当前线程中保存下一个过滤器
	 */
	private static final ThreadLocal<Integer> nextFilterIndex = new ThreadLocal<Integer>();
	
	/**
	 * 过滤器链
	 */
	private List<Filter> filterChains = new ArrayList<Filter>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
	
	/**
	 * 实现一个代理方法代替ServletApi提供的方法
	 * 以接管子过滤器中对下一级过滤器的调用
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		Filter nextFilter = filterChains.get(nextFilterIndex.get());
		if (filterChains.size() > nextFilterIndex.get()+1) {
			nextFilterIndex.set(nextFilterIndex.get()+1);
		} else {
			nextFilterIndex.remove();
			servletApiFilterChain.get().doFilter(request, response);
			return;
		}
		nextFilter.doFilter(request, response, this);
	}
	
	/**
	 * 实现一个过滤器，
	 * 该过滤器配置到Web.xml中，再由该过滤器加载处理其它过滤器（从Spring中扫描，按照优先级处理）
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (filterChains.size() == 0) {
			logger.info("filter proxy chains is empty");
			chain.doFilter(request, response);
			
		} else {
			// filterChains.size() >= 1
			servletApiFilterChain.set(chain);
			nextFilterIndex.set(0);
			filterChains.get(0).doFilter(request, response, this);
			
		}
	}
	
	/**
	 * 扫描处理器链
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if ("Root WebApplicationContext".equals(event.getApplicationContext().getDisplayName())) {
			Map<String, FilterProxyWrap> filterMap = event.getApplicationContext().getBeansOfType(FilterProxyWrap.class);
			List<FilterProxyWrap> filters = new ArrayList<FilterProxyWrap>(filterMap.values());
			Collections.sort(filters, new Comparator<FilterProxyWrap>() {
				@Override
				public int compare(FilterProxyWrap o1, FilterProxyWrap o2) {
					return o2.getPriority() - o1.getPriority();
				}
			});
			for (FilterProxyWrap f : filters) {
				if (!filterChains.contains(f.getFilter())) {
					filterChains.add(f.getFilter());
				}
			}
			logger.info("filter proxy init complate, filter chain [{}]", filterChains.toString());
		} else {
			logger.debug("ignore ContextRefreshedEvent on display name : " + event.getApplicationContext().getDisplayName());
		}
	}

}
