package serv.web.api.inter;

/**
 * 鉴权异常
 * 
 * @author shiying
 *
 */
public class ApiUserAuthenticationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3787786050681617917L;

	private String err_code;
	
	private String err_msg;

	public ApiUserAuthenticationException(String err_code, String err_msg) {
		super();
		this.err_code = err_code;
		this.err_msg = err_msg;
	}

	public ApiUserAuthenticationException(String message, Throwable cause, String err_code, String err_msg) {
		super(message, cause);
		this.err_code = err_code;
		this.err_msg = err_msg;
	}

	public ApiUserAuthenticationException(String message, String err_code, String err_msg) {
		super(message);
		this.err_code = err_code;
		this.err_msg = err_msg;
	}

	public ApiUserAuthenticationException(Throwable cause, String err_code, String err_msg) {
		super(cause);
		this.err_code = err_code;
		this.err_msg = err_msg;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

}
