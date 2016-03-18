package com.github.gserv.serv.wx.support.api.statistics;

import java.util.Date;

/**
 * 用户日统计数据
 * 
 * @author shiying
 *
 */
public class StatisticsUserCumulate {
	
	/**
	 * 数据的日期
	 */
	private Date ref_date;
	
	/**
	 * 总用户量
	 */
	private Integer cumulate_user;
	
	/**
	 * 新增用户总数，本地计算，所有类型总和
	 */
	private Integer new_user_count = 0;
	
	/**
	 * 取消用户总数
	 */
	private Integer cancel_user_count = 0;
	
	/**
	 * 0代表其他（包括带参数二维码）
	 */
	private Integer new_user_other = 0;
	
	/**
	 * 3代表扫二维码
	 */
	private Integer new_user_scan = 0;
	
	/**
	 * 17代表名片分享
	 */
	private Integer new_user_card = 0;
	
	/**
	 * 35代表搜号码（即微信添加朋友页的搜索）
	 */
	private Integer new_user_searchcode = 0;
	
	/**
	 * 39代表查询微信公众帐号
	 */
	private Integer new_user_searchname = 0;
	
	/**
	 * 43代表图文页右上角菜单
	 */
	private Integer new_user_news = 0;
	

	public Date getRef_date() {
		return ref_date;
	}

	public void setRef_date(Date ref_date) {
		this.ref_date = ref_date;
	}

	public Integer getCumulate_user() {
		return cumulate_user;
	}

	public void setCumulate_user(Integer cumulate_user) {
		this.cumulate_user = cumulate_user;
	}

	public Integer getNew_user_count() {
		return new_user_count;
	}

	public void setNew_user_count(Integer new_user_count) {
		this.new_user_count = new_user_count;
	}

	public Integer getCancel_user_count() {
		return cancel_user_count;
	}

	public void setCancel_user_count(Integer cancel_user_count) {
		this.cancel_user_count = cancel_user_count;
	}

	public Integer getNew_user_other() {
		return new_user_other;
	}

	public void setNew_user_other(Integer new_user_other) {
		this.new_user_other = new_user_other;
	}

	public Integer getNew_user_scan() {
		return new_user_scan;
	}

	public void setNew_user_scan(Integer new_user_scan) {
		this.new_user_scan = new_user_scan;
	}

	public Integer getNew_user_card() {
		return new_user_card;
	}

	public void setNew_user_card(Integer new_user_card) {
		this.new_user_card = new_user_card;
	}

	public Integer getNew_user_searchcode() {
		return new_user_searchcode;
	}

	public void setNew_user_searchcode(Integer new_user_searchcode) {
		this.new_user_searchcode = new_user_searchcode;
	}

	public Integer getNew_user_searchname() {
		return new_user_searchname;
	}

	public void setNew_user_searchname(Integer new_user_searchname) {
		this.new_user_searchname = new_user_searchname;
	}

	public Integer getNew_user_news() {
		return new_user_news;
	}

	public void setNew_user_news(Integer new_user_news) {
		this.new_user_news = new_user_news;
	}
	
	

}
