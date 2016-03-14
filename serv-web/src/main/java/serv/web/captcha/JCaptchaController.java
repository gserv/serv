package serv.web.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 验证码获取接口
 * 
 * @author shiying
 *
 */
public class JCaptchaController implements org.springframework.web.servlet.mvc.Controller {
	
	/**
	 * 请求图形验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	response.setDateHeader("Expires", 0L);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        String id = request.getRequestedSessionId();  
        BufferedImage bi = JCaptcha.captchaService.getImageChallengeForID(id);  
        ServletOutputStream out = response.getOutputStream();  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }
        return null;
	}

}
