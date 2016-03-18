package com.github.gserv.serv.wx.message.revc;

import org.dom4j.Element;

/**
 * 接收事件消息类型
 * 
 * @author shiying
 *
 */
public class RevcEventMessage extends AbstractRevcMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6562886914795600022L;
	
	/**
	 * 事件类型
	 */
	private String Event;
	
	/**
	 * 事件KEY值
	 */
	private String EventKey;
	
	/**
	 * 二维码的ticket，可用来换取二维码图片
	 */
	private String Ticket;
	
	/**
	 * 地理位置纬度
	 */
	private String Latitude;
	
	/**
	 * 地理位置经度
	 */
	private String Longitude;
	
	/**
	 * 地理位置精度
	 */
	private String Precision;
	
	/**
	 * 群发的消息ID，与通知消息的ID不同
	 */
	private Long MsgID;
	
	/**
	 * 群发的结构，为“send success”或“send fail”或“err(num)”。
	 * 但send success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。
	 * err(num)是审核失败的具体原因，可能的情况如下：
	 * 		err(10001), //涉嫌广告 
	 * 		err(20001), //涉嫌政治 
	 * 		err(20004), //涉嫌社会 
	 * 		err(20002), //涉嫌色情 
	 * 		err(20006), //涉嫌违法犯罪 
	 * 		err(20008), //涉嫌欺诈 
	 * 		err(20013), //涉嫌版权 
	 * 		err(22000), //涉嫌互推(互相宣传) 
	 * 		err(21000), //涉嫌其他
	 */
	private String Status;
	
	/**
	 * group_id下粉丝数；或者openid_list中的粉丝数
	 */
	private Integer TotalCount;
	
	/**
	 * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount
	 */
	private Integer FilterCount;
	
	/**
	 * 发送成功的粉丝数
	 */
	private Integer SentCount;
	
	/**
	 * 发送失败的粉丝数
	 */
	private Integer ErrorCount;
	

	public RevcEventMessage() {
		super();
	}

	@Override
	protected void parseProperties(Element xmlElement) {
		this.setEvent(xmlElement.elementText("Event"));
		this.setEventKey(xmlElement.elementText("EventKey"));
		this.setTicket(xmlElement.elementText("Ticket"));
		this.setLatitude(xmlElement.elementText("Latitude"));
		this.setLongitude(xmlElement.elementText("Longitude"));
		this.setPrecision(xmlElement.elementText("Precision"));
		this.setMsgID(xmlElement.elementText("MsgID") == null ? null : Long.parseLong(xmlElement.elementText("MsgID")));
		this.setStatus(xmlElement.elementText("Status"));
		this.setTotalCount(xmlElement.elementText("TotalCount") == null ? null : Integer.parseInt(xmlElement.elementText("TotalCount")));
		this.setFilterCount(xmlElement.elementText("FilterCount") == null ? null : Integer.parseInt(xmlElement.elementText("FilterCount")));
		this.setSentCount(xmlElement.elementText("SentCount") == null ? null : Integer.parseInt(xmlElement.elementText("SentCount")));
		this.setErrorCount(xmlElement.elementText("ErrorCount") == null ? null : Integer.parseInt(xmlElement.elementText("ErrorCount")));
		super.parseProperties(xmlElement);
	}

	@Override
	protected void setProperties(Element xmlElement) {
		if (this.getEvent() != null) xmlElement.addElement("Event").setText(this.getEvent().toString());
		if (this.getEventKey() != null) xmlElement.addElement("EventKey").setText(this.getEventKey().toString());
		if (this.getTicket() != null) xmlElement.addElement("Ticket").setText(this.getTicket().toString());
		if (this.getLatitude() != null) xmlElement.addElement("Latitude").setText(this.getLatitude().toString());
		if (this.getLongitude() != null) xmlElement.addElement("Longitude").setText(this.getLongitude().toString());
		if (this.getPrecision() != null) xmlElement.addElement("Precision").setText(this.getPrecision().toString());
		if (this.getMsgID() != null) xmlElement.addElement("MsgID").setText(this.getMsgID().toString());
		if (this.getStatus() != null) xmlElement.addElement("Status").setText(this.getStatus().toString());
		if (this.getTotalCount() != null) xmlElement.addElement("TotalCount").setText(this.getTotalCount().toString());
		if (this.getFilterCount() != null) xmlElement.addElement("FilterCount").setText(this.getFilterCount().toString());
		if (this.getSentCount() != null) xmlElement.addElement("SentCount").setText(this.getSentCount().toString());
		if (this.getErrorCount() != null) xmlElement.addElement("ErrorCount").setText(this.getErrorCount().toString());
		super.setProperties(xmlElement);
	}
	
	

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		Precision = precision;
	}

	public Long getMsgID() {
		return MsgID;
	}

	public void setMsgID(Long msgID) {
		MsgID = msgID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Integer getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(Integer totalCount) {
		TotalCount = totalCount;
	}

	public Integer getFilterCount() {
		return FilterCount;
	}

	public void setFilterCount(Integer filterCount) {
		FilterCount = filterCount;
	}

	public Integer getSentCount() {
		return SentCount;
	}

	public void setSentCount(Integer sentCount) {
		SentCount = sentCount;
	}

	public Integer getErrorCount() {
		return ErrorCount;
	}

	public void setErrorCount(Integer errorCount) {
		ErrorCount = errorCount;
	}

	
	
	
	
	
	
	
	
	
	
}














