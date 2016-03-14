package serv.web.shiro.user.simple;

import java.util.List;

import serv.web.shiro.user.ShiroUser;

/**
 * 简易实现
 * 
 * @author shiying
 *
 */
public class ShiroUserSimple implements ShiroUser {
	
	private String loginUsername;
	
	private String loginPasswordHash;
	
	private String loginSalt;
	
	private Boolean stateDisabled;
	
	private List<String> stringPermission;
	
	private List<String> roles;
	
	

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPasswordHash() {
		return loginPasswordHash;
	}

	public void setLoginPasswordHash(String loginPasswordHash) {
		this.loginPasswordHash = loginPasswordHash;
	}

	public String getLoginSalt() {
		return loginSalt;
	}

	public void setLoginSalt(String loginSalt) {
		this.loginSalt = loginSalt;
	}

	public Boolean getStateDisabled() {
		return stateDisabled;
	}

	public void setStateDisabled(Boolean stateDisabled) {
		this.stateDisabled = stateDisabled;
	}

	public List<String> getStringPermission() {
		return stringPermission;
	}

	public void setStringPermission(List<String> stringPermission) {
		this.stringPermission = stringPermission;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
	
	
	
	
	
	

}
