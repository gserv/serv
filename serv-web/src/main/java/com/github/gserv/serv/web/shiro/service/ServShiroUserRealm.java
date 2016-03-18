package com.github.gserv.serv.web.shiro.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.web.shiro.user.ShiroUser;
import com.github.gserv.serv.web.shiro.user.ShiroUserContext;
import com.github.gserv.serv.web.shiro.user.ShiroUserInter;


public class ServShiroUserRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(ServShiroUserRealm.class);
	
	
	private ShiroUserInter shiroUserInter;
	

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	//--授权代码
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // RememberMe 后容错处理
        if (ShiroUserContext.getShiroUser() == null) {
        	this.setSession(
        			ShiroUserContext.getShiroUserSessionKey(), 
        			shiroUserInter.getManagerUserByUsername(ShiroUserContext.getLoginUsername()));
        }
        ShiroUser shiroUser = ShiroUserContext.getShiroUser();
        authorizationInfo.addRoles(shiroUser.getRoles());
        authorizationInfo.addStringPermissions(shiroUser.getStringPermission());
        //
        return authorizationInfo;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	//---认证代码--
        String username = (String) token.getPrincipal();
        if (username == null || username.equals("")) {
        	throw new AuthenticationException("UnknownAccountException");	//没找到帐号
        }
        ShiroUser shiroUser = shiroUserInter.getManagerUserByUsername(username);
        if (shiroUser == null) {
        	throw new AuthenticationException("UnknownAccountException");	//没找到帐号
        }
        if (shiroUser.getStateDisabled()) {
            throw new LockedAccountException("LockedAccountException"); //帐号锁定
        }
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		shiroUser.getLoginUsername(), //用户名
        		shiroUser.getLoginPasswordHash(), //密码
        		ByteSource.Util.bytes(shiroUser.getLoginSalt()),
        		//  ByteSource.Util.bytes("saft"),	//salt=username+salt
                getName()  //realm name
        );
        this.setSession(ShiroUserContext.getShiroUserSessionKey(), shiroUser);
        
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
    
    /** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            logger.info("新用户访问，Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    }

	public ShiroUserInter getShiroUserInter() {
		return shiroUserInter;
	}

	public void setShiroUserInter(ShiroUserInter shiroUserInter) {
		this.shiroUserInter = shiroUserInter;
	} 
    
    
    
    
}
