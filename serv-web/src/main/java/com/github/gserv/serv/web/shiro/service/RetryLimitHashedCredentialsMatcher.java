package com.github.gserv.serv.web.shiro.service;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;


public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	
	@Resource(name="credentialsMatcher")
	private RetryLimitHashedCredentialsMatcher credentialsMatcher;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
      //  String username = (String) token.getPrincipal();
        //
        AtomicInteger retryCount = null;
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
        }
        if(retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException("ExcessiveAttemptsException");
        }
        //--检测密码是否正确--
        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
        }
        return matches;
    }
    
    
    public String generatorCode(String pwd, String salt) {
//    	String salt =  new SecureRandomNumberGenerator().nextBytes().toHex();
    	SimpleHash simpleHash = new SimpleHash(this.getHashAlgorithmName(), pwd, salt, this.getHashIterations());
    	return simpleHash.toHex();
    }
    
    
    public String generatorSalt() {
    	return new SecureRandomNumberGenerator().nextBytes().toHex();
    }
    
    
}
