package serv.web.api.mem;

import serv.web.api.inter.ApiUserTokenSupport;

public class SimpleApiUserTokenSupport implements ApiUserTokenSupport {

	// 接入号
	private String appId;
	
	// 私钥
	private String privateKey;
	
	// 令牌过期时间
	private int tokenExpire;
	
	@Override
	public String getAppId() {
		return appId;
	}

	@Override
	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String getPrivateKey() {
		return privateKey;
	}

	@Override
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public int getTokenExpire() {
		return tokenExpire;
	}

	@Override
	public void setTokenExpire(int tokenExpire) {
		this.tokenExpire = tokenExpire;
	}
	
	
	

}
