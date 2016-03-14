package serv.web.api.inter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import serv.commons.JsonUtils;
import serv.commons.encry.AESUtils;
import serv.commons.encry.Base64Util;

/**
 * 令牌管理器
 * 
 * @author shiying@caituo
 *
 */
@Service
public class ApiTokenService {
	
	/**
	 * 用户服务
	 */
	private ApiUserService apiUserService;
	
	/**
	 * 缓存名称
	 */
	private String cacheName = "servWebApiCacheToken";
	
	/**
	 * Token时间戳
	 */
	private String tokenTimestampFormat = "yyyyMMddHHmmssSSS";
	
	/**
	 * 缓存管理器
	 */
    private CacheManager cacheManager;
	
    /**
     * TokenAes加密私钥
     */
	private String tokenAesPrivateKey = "default";
	
	/**
	 * 转换Token成字符串
	 * @param tokenInfo
	 * @return
	 */
	public String convertTokenInfoToString(ApiTokenInfo tokenInfo) {
//		String infoStr = tokenInfo.getAppId() 
//				+ "#" + tokenInfo.getTimestamp() 
//				+ "#" + tokenInfo.getExpire()
//				+ "#" + tokenInfo.getRand()
//				+ "#" + JsonUtils.toJson(tokenInfo.getParmar());
//		String base64 = Base64Util.encodeForString(infoStr);
//		String aes = AESUtils.encryptStr(base64, tokenAesPrivateKey);
//		return Base64Util.encodeForString(aes);
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 获取令牌
	 * @param token
	 * @return
	 */
	public ApiTokenInfo getToken(String token) {
		Cache cache = cacheManager.getCache(cacheName);
		Element e = (Element) cache.get(token);
		if (e == null) {
			return null;
		}
		return (ApiTokenInfo) e.getValue();
	}
	
	/**
	 * 生成Token
	 * @param appid 接入号
	 * @return
	 */
	public String generatorToken(String accessId, Object...parmar) {
		//
		ApiUserTokenSupport apiUserTokenSupport = apiUserService.getApiUserTokenSupportByAccessId(accessId);
		//
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			throw new RuntimeException("not found cache name : " + cacheName);
		}
		DateFormat dateFormat = new SimpleDateFormat(tokenTimestampFormat);
		// 清除旧Token
		ApiTokenInfo info = new ApiTokenInfo();
		// 时间戳
		Date time = new Date();
		Date exp = new Date(time.getTime() + apiUserTokenSupport.getTokenExpire()*1000);
		// 生成新的Token
		info.setAppId(accessId);
		info.setTimestamp(dateFormat.format(time));
		info.setExpire(dateFormat.format(exp));
		info.setRand(UUID.randomUUID().toString().replaceAll("-", ""));
		info.setParmar(parmar);
		String tokenStr = convertTokenInfoToString(info);
		// 写入缓存
		Element e = new Element(tokenStr, info);
		e.setTimeToLive((int) apiUserTokenSupport.getTokenExpire());
		e.setTimeToIdle((int) apiUserTokenSupport.getTokenExpire());
		cache.put(e);
		//
		return tokenStr;
	}
	
	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getTokenTimestampFormat() {
		return tokenTimestampFormat;
	}

	public void setTokenTimestampFormat(String tokenTimestampFormat) {
		this.tokenTimestampFormat = tokenTimestampFormat;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String getTokenAesPrivateKey() {
		return tokenAesPrivateKey;
	}

	public void setTokenAesPrivateKey(String tokenAesPrivateKey) {
		this.tokenAesPrivateKey = tokenAesPrivateKey;
	}

	public ApiUserService getApiUserService() {
		return apiUserService;
	}

	public void setApiUserService(ApiUserService apiUserService) {
		this.apiUserService = apiUserService;
	}

	
	
	
}
