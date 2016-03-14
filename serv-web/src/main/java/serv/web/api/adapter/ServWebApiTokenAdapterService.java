package serv.web.api.adapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.HttpUtils;
import serv.commons.JsonUtils;
import serv.commons.encry.HashUtils;

/**
 * Api临时令牌请求服务
 * 
 * @author shiying
 *
 */
public class ServWebApiTokenAdapterService {
	private static final Logger logger = LoggerFactory.getLogger(ServWebApiTokenAdapterService.class);
	
	private String baseServerUrl = "";
	
	/**
	 * Token请求服务
	 */
	private String apiTokenRequestUrl = "serv/web/api/apiTokenRequest.do";
	
	/**
	 * Token验证服务
	 */
	private String apiTokenValidateUrl = "serv/web/api/apiTokenValidate.do";
	
	/**
	 * 接入accessId
	 */
	private String accessId;
	
	/**
	 * 接入私钥
	 */
	private String privateKey;
	
	
	/**
	 * 请求ApiToken
	 * 
	 * @param parmar 令牌创建参数
	 * @return
	 * @throws IOException
	 */
	public String apiTokenRequest(String...parmar) throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Map<String, String> parmars = new HashMap<String, String>();
		parmars.put("accessid", accessId);
		parmars.put("timestamp", timestamp);
		parmars.put("sign", HashUtils.md5(accessId + timestamp + privateKey));
		parmars.put("parmar", JsonUtils.toJson(parmar));
		String res = HttpUtils.get(baseServerUrl + apiTokenRequestUrl, parmars);
		logger.debug("api token request, accessid[{}], res >> {}", accessId, res);
		Map<String, Object> resObj = JsonUtils.parseJsonMap(res);
		return (String) resObj.get("token");
	}
	
	
	public String getBaseServerUrl() {
		return baseServerUrl;
	}

	public void setBaseServerUrl(String baseServerUrl) {
		this.baseServerUrl = baseServerUrl;
	}

	public String getApiTokenRequestUrl() {
		return apiTokenRequestUrl;
	}

	public void setApiTokenRequestUrl(String apiTokenRequestUrl) {
		this.apiTokenRequestUrl = apiTokenRequestUrl;
	}

	public String getApiTokenValidateUrl() {
		return apiTokenValidateUrl;
	}

	public void setApiTokenValidateUrl(String apiTokenValidateUrl) {
		this.apiTokenValidateUrl = apiTokenValidateUrl;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	

}
