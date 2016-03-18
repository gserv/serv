package com.github.gserv.serv.wx.support.api.statistics;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.gserv.serv.commons.HttpUtils;
import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.wx.service.manager.WxService;
import com.github.gserv.serv.wx.support.WxApiInvorkException;

/**
 * 微信统计接口
 * 
 * @author shiying
 *
 */
public class WxStatisticsService implements WxService {
	private static final Logger logger = LoggerFactory.getLogger(WxStatisticsService.class);
	
	private WxServiceManager wxServiceManager;
	
	
	private String url_getarticletotal = "https://api.weixin.qq.com/datacube/getarticletotal";
	
	private String url_getusersummary = "https://api.weixin.qq.com/datacube/getusersummary";
	
	private String url_getusercumulate = "https://api.weixin.qq.com/datacube/getusercumulate";
	
	private int retryMax = 5;
	
	
	public WxStatisticsService() {
		ConvertUtils.register(new Converter() {
			 
		    private static final String DATE      = "yyyy-MM-dd";
		    private static final String DATETIME  = "yyyy-MM-dd HH:mm:ss";
		    private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";
		 
		    @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
		    public Object convert(Class type, Object value) {
		        return toDate(type, value);
		    }

		    @SuppressWarnings({ "rawtypes" })
		    public Object toDate(Class type, Object value) {
		        if (value == null || "".equals(value))
		            return null;
		        if (value instanceof String) {
		            String dateValue = value.toString().trim();
		            int length = dateValue.length();
		            if (type.equals(Date.class)) {
		                try {
		                    DateFormat formatter = null;
		                    if (length <= 10) {
		                        formatter = new SimpleDateFormat(DATE);
		                        return formatter.parse(dateValue);
		                    }
		                    if (length <= 19) {
		                        formatter = new SimpleDateFormat(DATETIME);
		                        return formatter.parse(dateValue);
		                    }
		                    if (length <= 23) {
		                        formatter = new SimpleDateFormat(TIMESTAMP);
		                        return formatter.parse(dateValue);
		                    }
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        }
		        return value;
		    }
		}, Date.class);
	}

	
	/**
	 * 获取data这一天群发的所有内容，截止到data+7天中所有访问统计
	 * @param date
	 * @throws WxApiInvorkException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<StatisticsNewsItem> getarticletotal(Date date) throws WxApiInvorkException {
		String url = url_getarticletotal + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("begin_date", new SimpleDateFormat("yyyy-MM-dd").format(date));
		data.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(date));
		int time = 0;
		String res = null;
		while ((time++) < retryMax) {
			try {
				res = HttpUtils.post(url, JsonUtils.toJson(data));
				break;
			} catch (IOException e1) {
				logger.warn("http invork faild, retry " + time);
			}
		}
		if (res == null) {
			throw new WxApiInvorkException("http invork faild");
		}
		Map map = null;
		try {
			map = new ObjectMapper().readValue(res, Map.class);
		} catch (Exception e1) {
			throw new WxApiInvorkException("response resolve faild", e1);
		}
		//
		List<StatisticsNewsItem> items = new ArrayList<StatisticsNewsItem>();
		if (map.get("list") == null) {
			logger.warn("统计结果获取失败。res=" + res);
			throw new WxApiInvorkException((Integer) map.get("errcode"), (String) map.get("errmsg"));
		}
		for (Map m : (List<Map>) map.get("list")) {
			StatisticsNewsItem item = new StatisticsNewsItem();
			try {
				BeanUtils.populate(item, m);
				item.setDetails(new ArrayList<StatisticsNewsItemDetails>());
				for (Map d : (List<Map>) m.get("details")) {
					StatisticsNewsItemDetails detail = new StatisticsNewsItemDetails();
					BeanUtils.populate(detail, d);
					item.getDetails().add(detail);
				}
				items.add(item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return items;
	}
	
	/**
	 * 获得用户变化统计数据
	 * @return
	 * @throws WxApiInvorkException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public List<StatisticsUserCumulate> getusercumulate(Date start, Date end) throws WxApiInvorkException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("begin_date", new SimpleDateFormat("yyyy-MM-dd").format(start));
		data.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(end));
		//
		String res_getusersummary = null;
		try {
			res_getusersummary = HttpUtils.post(
					url_getusercumulate + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken(), 
					JsonUtils.toJson(data));
		} catch (IOException e) {
			throw new WxApiInvorkException("http invork faild");
		}
		List<StatisticsUserCumulate> list = JsonUtils.parseJson(
				JsonUtils.toJson(JsonUtils.parseJsonMap(res_getusersummary).get("list")), 
				List.class, StatisticsUserCumulate.class);
		if (list == null) {
			@SuppressWarnings("rawtypes")
			Map obj = JsonUtils.parseJsonMap(res_getusersummary);
			if (obj.get("errcode") != null && obj.get("errcode") instanceof Integer) {
				throw new WxApiInvorkException((Integer) obj.get("errcode"), (String) obj.get("errmsg"));
			} else {
				throw new WxApiInvorkException(res_getusersummary);
			}
		}
		for (StatisticsUserCumulate stat : list) {
			//
			Map<String, Object> summaryData = new HashMap<String, Object>();
			summaryData.put("begin_date", new SimpleDateFormat("yyyy-MM-dd").format(stat.getRef_date()));
			summaryData.put("end_date", new SimpleDateFormat("yyyy-MM-dd").format(stat.getRef_date()));
			//
			String res_getusercumulate = null;
			try {
				res_getusercumulate = HttpUtils.post(
						url_getusersummary + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken(), 
						JsonUtils.toJson(summaryData));
			} catch (IOException e) {
				throw new WxApiInvorkException("http invork faild");
			}
			for (StatisticsUserSummary summaryLine : (List<StatisticsUserSummary>) JsonUtils.parseJson(
					JsonUtils.toJson(JsonUtils.parseJsonMap(res_getusercumulate).get("list")), List.class, StatisticsUserSummary.class)) {
				if (stat.getRef_date().equals(summaryLine.getRef_date())) {
					/**
					 * 0代表其他（包括带参数二维码） 
					 * 3代表扫二维码 
					 * 17代表名片分享 
					 * 35代表搜号码（即微信添加朋友页的搜索） 
					 * 39代表查询微信公众帐号 
					 * 43代表图文页右上角菜单
					 */
					switch (summaryLine.getUser_source()) {
					case 0 : 
						stat.setNew_user_other(summaryLine.getNew_user());
						stat.setNew_user_count(stat.getNew_user_count() + summaryLine.getNew_user());
						stat.setCancel_user_count(stat.getCancel_user_count() + summaryLine.getCancel_user());
						break;
					case 3 : 
						stat.setNew_user_scan(summaryLine.getNew_user());
						stat.setNew_user_count(stat.getNew_user_count() + summaryLine.getNew_user());
						stat.setCancel_user_count(stat.getCancel_user_count() + summaryLine.getCancel_user());
						break;
					case 17 : 
						stat.setNew_user_card(summaryLine.getNew_user());
						stat.setNew_user_count(stat.getNew_user_count() + summaryLine.getNew_user());
						stat.setCancel_user_count(stat.getCancel_user_count() + summaryLine.getCancel_user());
						break;
					case 35 : 
						stat.setNew_user_searchcode(summaryLine.getNew_user());
						stat.setNew_user_count(stat.getNew_user_count() + summaryLine.getNew_user());
						stat.setCancel_user_count(stat.getCancel_user_count() + summaryLine.getCancel_user());
						break;
					case 39 : 
						stat.setNew_user_searchname(summaryLine.getNew_user());
						stat.setNew_user_count(stat.getNew_user_count() + summaryLine.getNew_user());
						stat.setCancel_user_count(stat.getCancel_user_count() + summaryLine.getCancel_user());
						break;
					case 43 : 
						stat.setNew_user_news(summaryLine.getNew_user());
						stat.setNew_user_count(stat.getNew_user_count() + summaryLine.getNew_user());
						stat.setCancel_user_count(stat.getCancel_user_count() + summaryLine.getCancel_user());
						break;
					default :
						stat.setNew_user_count(stat.getNew_user_count() + summaryLine.getNew_user());
						stat.setCancel_user_count(stat.getCancel_user_count() + summaryLine.getCancel_user());
						break;
					} 
				}
			}
		}
		return list;
	}
	
	
	private static class StatisticsUserSummary {
		private Date ref_date;
		private Integer user_source;
		private Integer new_user;
		private Integer cancel_user;
		public Date getRef_date() {
			return ref_date;
		}
		@SuppressWarnings("unused")
		public void setRef_date(Date ref_date) {
			this.ref_date = ref_date;
		}
		public Integer getUser_source() {
			return user_source;
		}
		@SuppressWarnings("unused")
		public void setUser_source(Integer user_source) {
			this.user_source = user_source;
		}
		public Integer getNew_user() {
			return new_user;
		}
		@SuppressWarnings("unused")
		public void setNew_user(Integer new_user) {
			this.new_user = new_user;
		}
		public Integer getCancel_user() {
			return cancel_user;
		}
		@SuppressWarnings("unused")
		public void setCancel_user(Integer cancel_user) {
			this.cancel_user = cancel_user;
		}
		
	}


	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}


	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	

}
