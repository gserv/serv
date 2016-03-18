package com.github.gserv.serv.web.api.mem;

import java.util.ArrayList;
import java.util.List;

import com.github.gserv.serv.web.api.inter.ApiUserService;
import com.github.gserv.serv.web.api.inter.ApiUserTokenSupport;

/**
 * 内存数据加载器
 * 
 * @author shiying
 *
 */
public class ApiUserServiceInMemery implements ApiUserService {
	
	private List<ApiUserTokenSupport> data = new ArrayList<ApiUserTokenSupport>();

	@Override
	public ApiUserTokenSupport getApiUserTokenSupportByAccessId(String accessid) {
		for (ApiUserTokenSupport d : data) {
			if (d.getAppId().equals(accessid)) {
				return d;
			}
		}
		return null;
	}

	public List<ApiUserTokenSupport> getData() {
		return data;
	}

	public void setData(List<ApiUserTokenSupport> data) {
		this.data = data;
	}
	
	
	

}
