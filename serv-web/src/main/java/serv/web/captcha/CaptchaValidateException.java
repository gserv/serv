package serv.web.captcha;

import org.apache.shiro.authc.AuthenticationException;


/**
 * 图形验证码验证失败
 * 
 * @author shiying
 *
 */
public class CaptchaValidateException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3542101158374127375L;

	public CaptchaValidateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaptchaValidateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CaptchaValidateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CaptchaValidateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
