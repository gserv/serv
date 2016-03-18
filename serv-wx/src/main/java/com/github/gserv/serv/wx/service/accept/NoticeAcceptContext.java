package com.github.gserv.serv.wx.service.accept;

/**
 * 微信通知受理上下文
 * 
 * @author shiying
 *
 */
public class NoticeAcceptContext {
	
	/**
	 * 系统的接入ID，通过该ID查找访问的微信配置
	 */
	private String accessid;
	
	/**
	 * 接入ID签名，用来验证accessid的有效性
	 */
	private String sign; 
	
	/**
	 * 签名（微信接入验证）
	 */
	private String signature;
	
	/**
	 * 时间戳（微信接入验证）
	 */
	private String timestamp; 
	
	/**
	 * 随机数（微信接入验证）
	 */
	private String nonce; 
	
	/**
	 * 唯一字符串（微信接入验证）
	 */
	private String echostr; 
	
	/**
	 * 接收内容
	 */
	private String reqbody;
	
	/**
	 * 接受请求的URL地址中BasePath
	 */
	private String serverBasePath;

	public String getAccessid() {
		return accessid;
	}

	public void setAccessid(String accessid) {
		this.accessid = accessid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

	public String getReqbody() {
		return reqbody;
	}

	public void setReqbody(String reqbody) {
		this.reqbody = reqbody;
	}

	public String getServerBasePath() {
		return serverBasePath;
	}

	public void setServerBasePath(String serverBasePath) {
		this.serverBasePath = serverBasePath;
	}

	

	
}
