package serv.wx.support.api.statistics;

import java.util.Date;
import java.util.List;

public class StatisticsNewsItem {
	
	/**
	 * 数据的日期，需在begin_date和end_date之间
	 * 在getarticletotal接口中，ref_date指的是文章群发出日期
	 */
	private Date ref_date;
	
	/**
	 * 这里的msgid实际上是由msgid（图文消息id，这也就是群发接口调用后返回的msg_data_id）和index（消息次序索引）组成， 例如12003_3， 
	 * 其中12003是msgid，即一次群发的消息的id； 3为index，假设该次群发的图文消息共5个文章（因为可能为多图文），3表示5个中的第3个
	 */
	private String msgid;
	
	/**
	 * 图文消息的标题
	 */
	private String title;
	
	/**
	 * 在获取图文阅读分时数据时才有该字段，代表用户从哪里进入来阅读该图文。0:会话;1.好友;2.朋友圈;3.腾讯微博;4.历史消息页;5.其他
	 */
	private String user_source;
	
	/**
	 * 统计详情
	 */
	private List<StatisticsNewsItemDetails> details;

	public Date getRef_date() {
		return ref_date;
	}

	public void setRef_date(Date ref_date) {
		this.ref_date = ref_date;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUser_source() {
		return user_source;
	}

	public void setUser_source(String user_source) {
		this.user_source = user_source;
	}

	public List<StatisticsNewsItemDetails> getDetails() {
		return details;
	}

	public void setDetails(List<StatisticsNewsItemDetails> details) {
		this.details = details;
	}
	
	

}
