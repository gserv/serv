package serv.web.shiro.user;

/**
 * 用户信息访问接口
 * 
 * @author shiying
 *
 */
public interface ShiroUserInter {
	
	
	/**
	 * 通过用户名加载登录用户
	 * @param username
	 * @return
	 */
	public ShiroUser getManagerUserByUsername(String username);

}
