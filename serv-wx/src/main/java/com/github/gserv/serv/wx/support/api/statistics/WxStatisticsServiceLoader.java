package com.github.gserv.serv.wx.support.api.statistics;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.service.manager.WxServiceLoader;

public class WxStatisticsServiceLoader implements WxServiceLoader<WxStatisticsService> {
	
	private String url_getarticletotal = "https://api.weixin.qq.com/datacube/getarticletotal";
	
	private String url_getusersummary = "https://api.weixin.qq.com/datacube/getusersummary";
	
	private String url_getusercumulate = "https://api.weixin.qq.com/datacube/getusercumulate";

	@Override
	public synchronized WxStatisticsService load(WxServiceManager wxServiceManager) {
		WxStatisticsService wxStatisticsService = new WxStatisticsService();
		wxStatisticsService.setWxServiceManager(wxServiceManager);
		return wxStatisticsService;
	}

}
