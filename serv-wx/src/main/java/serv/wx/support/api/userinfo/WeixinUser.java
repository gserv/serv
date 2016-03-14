package serv.wx.support.api.userinfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 微信用户信息
 * 
 * @author shiying
 *
 */
public class WeixinUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8307256121691974884L;

	/**
	 * 是否关注
	 */
	private boolean subscribe;
	
	/**
	 * Openid
	 */
	private String openid;
	
	/**
	 * 昵称的Base64
	 */
	private String nickname;
	
	/**
	 * 性别
	 */
	private Sex sex;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 用户所在国家
	 */
	private String country;
	
	/**
	 * 用户所在省份
	 */
	private String province;
	
	/**
	 * 用户的语言，简体中文为zh_CN
	 */
	private String language;

	/**
	 * 头像URL地址
	 */
	private String headimgurl;

	/**
	 * 最后关注时间
	 */
	private Date subscribe_time;
	
	/**
	 * Unionid, 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 */
	private String unionid;
	
	/**
	 * 公众号运营者对粉丝的备注
	 */
	private String remark;
	
	/**
	 * 用户所在的分组ID
	 */
	private Integer groupid;
	
	/**
	 * 特权信息
	 */
	private List<String> privilege;
	
	/**
	 * 性别枚举
	 * 
	 * @author shiying
	 *
	 */
	public static enum Sex {
		unknown, man, woman
	}

	public boolean isSubscribe() {
		return subscribe;
	}

	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(Date subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public List<String> getPrivilege() {
		return privilege;
	}

	public void setPrivilege(List<String> privilege) {
		this.privilege = privilege;
	}

	
	
}
