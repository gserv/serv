package serv.web.interception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import serv.web.context.HttpRequestContext;

/**
 * Session检查拦截器
 * 检查session参数名是否存在，当失败时进入onFaildUrl地址
 * 
 * @author shiying
 *
 */
public class SessionCheckInterception implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(SessionCheckInterception.class);
	
	/**
	 * 请求前要检查的属性
	 */
	private String preCheckSessionAttributeKey;
	
	/**
	 * 请求后清理的属性
	 */
	private String postClearSessionAttributeKey;
	
	/**
	 * 当检查失败进入的地址
	 */
	private String onFaildUrl;
	
	/**
	 * 回调地址的参数名称
	 */
	private String callbackUrlParameterName;
	
	/**
	 * 调试对象
	 */
	private Object debugObject;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (!StringUtils.isNoneEmpty(preCheckSessionAttributeKey)) {
			return true;
		}
		if (request.getSession().getAttribute(preCheckSessionAttributeKey) != null) {
			return true;
		}
		if (debugObject != null) {
			request.getSession().setAttribute(preCheckSessionAttributeKey, debugObject);
			logger.debug("进入URL[{}]，检查sessionKey[{}]失败，使用调试对象[{}] ", request.getRequestURI(), preCheckSessionAttributeKey, debugObject);
			return true;
		}
		logger.debug("进入URL[{}]，检查sessionKey[{}]失败，跳转到错误页面[{}] ", request.getRequestURI(), preCheckSessionAttributeKey, onFaildUrl);
		//
		StringBuffer urlbuf = new StringBuffer();
		urlbuf.append(request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()));
		urlbuf.append(onFaildUrl);
		if (StringUtils.isNoneEmpty(callbackUrlParameterName)) { 
			urlbuf.append(onFaildUrl.indexOf("?") == -1 ? "?" : "&");
			urlbuf.append(callbackUrlParameterName);
			urlbuf.append("=");
			urlbuf.append(HttpRequestContext.getContext().getRequestRelativelyUrl());
		}
		response.sendRedirect(urlbuf.toString());
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (StringUtils.isNoneEmpty(postClearSessionAttributeKey)) {
			request.getSession().removeAttribute(postClearSessionAttributeKey);
			logger.debug("退出URL[{}]，清理sessionKey[{}] ", request.getRequestURI(), postClearSessionAttributeKey);
		}
	}


	public String getPreCheckSessionAttributeKey() {
		return preCheckSessionAttributeKey;
	}

	public void setPreCheckSessionAttributeKey(String preCheckSessionAttributeKey) {
		this.preCheckSessionAttributeKey = preCheckSessionAttributeKey;
	}

	public String getOnFaildUrl() {
		return onFaildUrl;
	}

	public void setOnFaildUrl(String onFaildUrl) {
		this.onFaildUrl = onFaildUrl;
	}

	public Object getDebugObject() {
		return debugObject;
	}

	public void setDebugObject(Object debugObject) {
		this.debugObject = debugObject;
	}

	public String getPostClearSessionAttributeKey() {
		return postClearSessionAttributeKey;
	}

	public void setPostClearSessionAttributeKey(String postClearSessionAttributeKey) {
		this.postClearSessionAttributeKey = postClearSessionAttributeKey;
	}

	public String getCallbackUrlParameterName() {
		return callbackUrlParameterName;
	}

	public void setCallbackUrlParameterName(String callbackUrlParameterName) {
		this.callbackUrlParameterName = callbackUrlParameterName;
	}

	
	

}
