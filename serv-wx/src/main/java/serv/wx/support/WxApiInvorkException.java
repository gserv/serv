package serv.wx.support;

/**
 * 微信API调用异常
 * 
 * @author shiying
 *
 */
public class WxApiInvorkException extends Exception {
	
	private Integer errcode;
	
	private String errmsg;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4483678750497566084L;

	public WxApiInvorkException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WxApiInvorkException(Integer errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public WxApiInvorkException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public WxApiInvorkException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public WxApiInvorkException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	

}

