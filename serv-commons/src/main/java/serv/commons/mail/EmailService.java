package serv.commons.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

/**
 * 电子邮件服务
 * @author shiying
 *
 */
public class EmailService {
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	// 邮件发送服务
    private JavaMailSender mailSender;
    
    // 邮件来源
    private String mailFrom;
	
    // 模板配置
	private FreeMarkerConfigurer freeMarkerConfigurer;
	

	/**
	 * 发送邮件
	 * @param email 发送地址
	 * @param subject 主题
	 * @param tmpl 模板路径
	 */
    public void sendMessage(
			String email, String subject, 
			String tmpl) {
    	sendMessage(email, subject, tmpl, new EmailNoticeActionAdapter());
    }

	
	/**
	 * 发送邮件
	 * @param email 发送地址
	 * @param subject 主题
	 * @param tmpl 模板路径
	 * @param action 事件处理器
	 */
	public void sendMessage(
			String email, String subject, 
			String tmpl, EmailNoticeAction action) {
		try {
			// 准备邮件正文
			if (!tmpl.endsWith(".ftl")) {
				tmpl = tmpl + ".ftl";
			}
			// 准备数据
	        @SuppressWarnings("rawtypes")
			Map model = new HashMap();
	        action.setData(model);
	        // 实例化模板
	        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(tmpl); 
	        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	        // 设置邮件参数
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(mailContent, true);
			helper.setFrom(mailFrom);
			// 处理附件
			action.setAttachment(helper);
			// 发送邮件
			mailSender.send(message);
			// log
			/*
			try {
				FileUtils.writeStringToFile(
						new File("", new SimpleDateFormat("yyyyMM").format(new Date()) + ".log"), 
						"email_notice, timestamp["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"], email["
								+email+"], subject["+subject+"], mailContent["+mailContent.replaceAll("\r\n", "")+"] \r\n", 
						"UTF-8", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			
		} catch (Exception e) {
			logger.warn("send mail faild.", e);
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	/**
	 * 邮件通知接口
	 * 
	 * @author shiying
	 *
	 */
	public static class EmailNoticeActionAdapter implements EmailNoticeAction {

		@Override
		public void setData(@SuppressWarnings("rawtypes") Map model) {
		}

		@Override
		public void setAttachment(MimeMessageHelper helper) {
		}
		
	}
	
	/**
	 * 邮件事件
	 * @author shiying
	 *
	 */
	public static interface EmailNoticeAction {
		
		/**
		 * 设置数据
		 * @param model
		 */
		public void setData(@SuppressWarnings("rawtypes") Map model);
		
		/**
		 * 设置附件
		 * @param helper
		 * @throws MessagingException 
		 */
		public void setAttachment(MimeMessageHelper helper) throws MessagingException;
		
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}
	
	
	
	
}
