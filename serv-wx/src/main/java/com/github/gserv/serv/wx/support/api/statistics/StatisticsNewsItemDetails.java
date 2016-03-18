package com.github.gserv.serv.wx.support.api.statistics;

import java.util.Date;

public class StatisticsNewsItemDetails {
	
	/**
	 * 统计时间
	 */
	private Date stat_date;

	/**
	 * 送达人数，一般约等于总粉丝数（需排除黑名单或其他异常情况下无法收到消息的粉丝）
	 */
	private Integer target_user;

	/**
	 * 图文页（点击群发图文卡片进入的页面）的阅读人数
	 */
	private Integer int_page_read_user;

	/**
	 * 图文页的阅读次数
	 */
	private Integer int_page_read_count;

	/**
	 * 原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0
	 */
	private Integer ori_page_read_user;

	/**
	 * 原文页的阅读次数
	 */
	private Integer ori_page_read_count;
	

	/**
	 * 分享的人数
	 */
	private Integer share_user;

	/**
	 * 分享的次数
	 */
	private Integer share_count;

	/**
	 * 收藏的人数
	 */
	private Integer add_to_fav_user;

	/**
	 * 收藏的次数
	 */
	private Integer add_to_fav_count;

	public Date getStat_date() {
		return stat_date;
	}

	public void setStat_date(Date stat_date) {
		this.stat_date = stat_date;
	}

	public Integer getTarget_user() {
		return target_user;
	}

	public void setTarget_user(Integer target_user) {
		this.target_user = target_user;
	}

	public Integer getInt_page_read_user() {
		return int_page_read_user;
	}

	public void setInt_page_read_user(Integer int_page_read_user) {
		this.int_page_read_user = int_page_read_user;
	}

	public Integer getInt_page_read_count() {
		return int_page_read_count;
	}

	public void setInt_page_read_count(Integer int_page_read_count) {
		this.int_page_read_count = int_page_read_count;
	}

	public Integer getOri_page_read_user() {
		return ori_page_read_user;
	}

	public void setOri_page_read_user(Integer ori_page_read_user) {
		this.ori_page_read_user = ori_page_read_user;
	}

	public Integer getOri_page_read_count() {
		return ori_page_read_count;
	}

	public void setOri_page_read_count(Integer ori_page_read_count) {
		this.ori_page_read_count = ori_page_read_count;
	}

	public Integer getShare_user() {
		return share_user;
	}

	public void setShare_user(Integer share_user) {
		this.share_user = share_user;
	}

	public Integer getShare_count() {
		return share_count;
	}

	public void setShare_count(Integer share_count) {
		this.share_count = share_count;
	}

	public Integer getAdd_to_fav_user() {
		return add_to_fav_user;
	}

	public void setAdd_to_fav_user(Integer add_to_fav_user) {
		this.add_to_fav_user = add_to_fav_user;
	}

	public Integer getAdd_to_fav_count() {
		return add_to_fav_count;
	}

	public void setAdd_to_fav_count(Integer add_to_fav_count) {
		this.add_to_fav_count = add_to_fav_count;
	}
	
	

}
