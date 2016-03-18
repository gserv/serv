package com.github.gserv.serv.web.shiro.user.simple;

import java.util.ArrayList;
import java.util.List;

import com.github.gserv.serv.web.shiro.user.ShiroUser;
import com.github.gserv.serv.web.shiro.user.ShiroUserInter;

public class ShiroUserInterSimple implements ShiroUserInter {
	
	public List<ShiroUserSimple> data = new ArrayList<ShiroUserSimple>();

	@Override
	public ShiroUser getManagerUserByUsername(String username) {
		for (ShiroUser su : data) {
			if (su.getLoginUsername().equals(username)) {
				return su;
			}
		}
		return null;
	}

	public List<ShiroUserSimple> getData() {
		return data;
	}

	public void setData(List<ShiroUserSimple> data) {
		this.data = data;
	}
	
	
	

}
