package com.github.gserv.serv.web.api.inter;

/**
 * 用户ApiToken支持
 * 
 * @author shiying
 *
 */
public interface ApiUserTokenSupport {
	
	/**
	 * 接入号
	 * @return
	 */
	public String getAppId();
	
	/**
	 * 接入号
	 * @return
	 */
	public void setAppId(String appId);
	
	/**
	 * 私钥
	 * @return
	 */
	public String getPrivateKey();
	
	/**
	 * 私钥
	 * @return
	 */
	public void setPrivateKey(String privateKey);
	
	/**
	 * 令牌过期时间
	 * @return
	 */
	public int getTokenExpire();
	
	/**
	 * 令牌过期时间
	 * @return
	 */
	public void setTokenExpire(int tokenExpire);
	
	
	
	
	
	

}
