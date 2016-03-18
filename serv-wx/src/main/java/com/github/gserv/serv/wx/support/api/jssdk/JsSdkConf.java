package com.github.gserv.serv.wx.support.api.jssdk;

import java.io.Serializable;

/**
 * JSSDK配置信息
 * 
 * @author shiying
 *
 */
public class JsSdkConf implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3210078956944201562L;

	/**
	 * APPID,公众号的唯一标识
	 */
	private String appId;
	
	/**
	 * 生成签名的时间戳
	 */
	private String timestamp;
	
	/**
	 * 生成签名的随机串
	 */
	private String nonceStr;
	
	/**
	 * 签名
	 */
	private String signature;
	
	/**
	 * 计算签名的页面地址
	 */
	private String pageurl;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getPageurl() {
		return pageurl;
	}

	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}
	
	
	
	

}
