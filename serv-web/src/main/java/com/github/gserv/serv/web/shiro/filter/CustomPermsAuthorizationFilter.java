package com.github.gserv.serv.web.shiro.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.stereotype.Repository;


public class CustomPermsAuthorizationFilter extends AuthorizationFilter {


	@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException { 
    	
        Subject subject = getSubject(request, response); 
        String[] rolesArray = (String[]) mappedValue; 

        if (rolesArray == null || rolesArray.length == 0) { 
            //no roles specified, so nothing to check - allow access. 
            return true; 
        } 

//        Set<String> roles = CollectionUtils.asSet(rolesArray); 
//        return subject.hasAllRoles(roles); 
        
    	try {
    		for(int i=0;i<rolesArray.length;i++) {
    			if(subject.isPermitted(rolesArray[i])) {  
    				return true;  
    			}  
    		}  
    	} catch (Exception e) {
        	return false;
        }
        return false;  
    } 
    
    

}
