package com.github.gserv.serv.wx.conf;

/**
 * 微信公众帐号配置属性
 * 
 * @author shiying
 *
 */
public interface WxConf {
	
	/**
	 * 微信应用ID
	 * @return
	 */
	public String getAppId();
	
	/**
	 * 微信应用私钥
	 * @return
	 */
	public String getAppSecret();
	
	/**
	 * 开发者接入Token
	 * @return
	 */
	public String getToken();
	
	/**
	 * 消息加解密密钥
	 * @return
	 */
	public String getEncodingAESKey();
	
	/**
	 * 代理AccessToken接口地址
	 * @return
	 */
	public String getProxyAccessTokenUrl();
	
	/**
	 * 获得代理接入ID
	 * @return
	 */
	public String getProxyAccessTokenAccessId();
	
	/**
	 * 获得代理接入私钥
	 * @return
	 */
	public String getProxyAccessTokenAccessKey();
	
	
}





