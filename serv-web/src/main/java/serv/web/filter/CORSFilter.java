package serv.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import serv.commons.context.HttpRequestContext;

/**
 * Servlet Filter implementation class CORSFilter
 * 


  	<!-- CORS 跨域支持 -->
	<filter>
		<filter-name>CORSFilter</filter-name>
		<filter-class>serv.web.filter.CORSFilter</filter-class>
	    <init-param>
	      <param-name>matchUrl</param-name>
	      <param-value>.*</param-value>
	    </init-param>
	    <init-param>
	      <param-name>maxAge</param-name>
	      <param-value>3628800</param-value>
	    </init-param>
	    <init-param>
	      <param-name>headers</param-name>
	      <param-value>content-type</param-value>
	    </init-param>
	    <init-param>
	      <param-name>methods</param-name>
	      <param-value>GET, POST, PUT, DELETE, OPTIONS</param-value>
	    </init-param>
	    <init-param>
	      <param-name>origin</param-name>
	      <param-value>http://localhost,http://dq.17iwo.com</param-value>
	    </init-param>
	</filter>
	<filter-mapping>
		<filter-name>CORSFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
  <!-- / CORS 跨域支持 -->
  



 * 
 * 
 */
public class CORSFilter implements Filter {
	
	String matchUrl = "\\/?.*";
	String maxAge = "3628800";
	String headers = "content-type";
	String methods = "GET, POST, PUT, DELETE, OPTIONS";
	String origin = "*";

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		// instance
		res.addHeader("ACCEPT_REQUEST_INSTANCE", System.getProperty("tomcat.instance", "default"));
		// 跨域设置
		if (HttpRequestContext.getContext() != null && HttpRequestContext.getContext().getBasePath() != null) {
			String url = HttpRequestContext.getContext().getRequestRelativelyUrl();
			if (url.matches(matchUrl)) {
				res.addHeader("Access-Control-Allow-Origin", origin);
				res.addHeader("Access-Control-Allow-Credentials", "true");
				res.addHeader("Access-Control-Allow-Methods", methods);
				res.addHeader("Access-Control-Allow-Headers", headers);
				res.addHeader("Access-Control-Max-Age", maxAge);
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		matchUrl = fConfig.getInitParameter("matchUrl");
		maxAge = fConfig.getInitParameter("maxAge");
		headers = fConfig.getInitParameter("headers");
		methods = fConfig.getInitParameter("methods");
		origin = fConfig.getInitParameter("origin");
	}

    /**
     * Default constructor. 
     */
    public CORSFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

}
