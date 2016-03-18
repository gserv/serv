package serv.web.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * HTTP请求的上下文
 * 
 * @author shiying
 *
 */
public class HttpRequestContext {
	private static ThreadLocal<Context> threadLocal = new ThreadLocal<Context>();
	
	public static void onRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (threadLocal.get() == null) {
			threadLocal.set(new Context());
		}
		threadLocal.get().request = request;
		threadLocal.get().response = response;
		threadLocal.get().servletContext = threadLocal.get().request.getSession().getServletContext();
		threadLocal.get().requestUrl = request.getRequestURL().toString();
		// 解析相对路径
		String path = request.getContextPath();
		threadLocal.get().basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String baseUrl = request.getRequestURI();
		if (baseUrl.startsWith(request.getContextPath())) {
			baseUrl = baseUrl.substring(request.getContextPath().length(), baseUrl.length());
		}
		if (baseUrl.startsWith("/")) {
			baseUrl = baseUrl.substring(1, baseUrl.length());
		}
		threadLocal.get().requestRelativelyUrl = baseUrl;
	}
	
	public static Context getContext() {
		return threadLocal.get();
	}
	
	public static class Context {
		private ServletContext servletContext;
		private HttpServletRequest request;
		private HttpServletResponse response;
		private String requestUrl;
		private String basePath;
		private String requestRelativelyUrl;
		public ServletContext getServletContext() {
			return servletContext;
		}
		public void setServletContext(ServletContext servletContext) {
			this.servletContext = servletContext;
		}
		public HttpServletRequest getRequest() {
			return request;
		}
		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}
		public HttpServletResponse getResponse() {
			return response;
		}
		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}
		public String getBasePath() {
			return basePath;
		}
		public void setBasePath(String basePath) {
			this.basePath = basePath;
		}
		public String getRequestRelativelyUrl() {
			return requestRelativelyUrl;
		}
		public void setRequestRelativelyUrl(String requestRelativelyUrl) {
			this.requestRelativelyUrl = requestRelativelyUrl;
		}
		public String getRequestUrl() {
			return requestUrl;
		}
		public void setRequestUrl(String requestUrl) {
			this.requestUrl = requestUrl;
		}
		
	}

}
