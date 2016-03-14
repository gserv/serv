package serv.web.api.inter;

import java.io.Serializable;

/**
 * Token保存的信息
 * 
 * @author shiying@caituo
 *
 */
public class ApiTokenInfo implements Serializable {
	private static final long serialVersionUID = 4697745173938707628L;

	// 接入号
	private String appId;
	
	// 时间戳
	private String timestamp;
	
	// 过期时间
	private String expire;
	
	// 随机串
	private String rand;
	
	// 令牌参数
	private Object[] parmar;

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

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public Object[] getParmar() {
		return parmar;
	}

	public void setParmar(Object[] parmar) {
		this.parmar = parmar;
	}

	
	
}
