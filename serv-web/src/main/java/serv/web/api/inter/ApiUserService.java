package serv.web.api.inter;

/**
 * Api用户服务
 * 
 * @author shiying
 *
 */
public interface ApiUserService {
	
	
	/**
	 * 获取用户信息
	 * 
	 * @param accessid
	 * @param timestamp
	 * @param sign
	 * @return
	 * @throws ApiUserAuthenticationException
	 */
	public ApiUserTokenSupport getApiUserTokenSupportByAccessId(String accessId);
	
	
	
}
