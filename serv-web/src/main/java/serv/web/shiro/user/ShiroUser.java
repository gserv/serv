package serv.web.shiro.user;

import java.util.List;

/**
 * 登录用户信息接口
 * @author shiying
 *
 */
public interface ShiroUser {
	
	/**
	 * 用户名
	 * @return
	 */
    public String getLoginUsername();
    
    /**
     * 密码Hash
     * @return
     */
    public String getLoginPasswordHash();
    
    /**
     * 盐
     * @return
     */
    public String getLoginSalt();
    
    /**
     * 禁用状态
     * @return
     */
    public Boolean getStateDisabled();
    
    /**
     * 获得用户所具有的权限列表
     * @return
     */
    public List<String> getStringPermission();
    
    /**
     * 获得用户所具有的角色列表
     * @return
     */
    public List<String> getRoles();
    
    
    
    
}















