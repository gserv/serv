package com.github.gserv.serv.commons.mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.commons.mail.EmailService.EmailNoticeAction;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/commons/mail/applicationContext-email.xml")
public class TestEmailService {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TestEmailService.class);
	
	@Resource
	private EmailService emailService;
	
	@Test
	public void test() {
//		emailService.sendMessage("350106052@qq.com", "serv 测试单元：" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "testMail", new EmailNoticeAction() {
//
//			@SuppressWarnings({ "unchecked", "rawtypes" })
//			@Override
//			public void setData(Map model) {
//				model.put("name", "名称");
//				model.put("sendMsg", "ccccccccccc");
//			}
//
//			@Override
//			public void setAttachment(MimeMessageHelper helper) throws MessagingException {
//				ClassPathResource res = new ClassPathResource("com/github/gserv/serv/commons/mail/tmpl/testMail_logo.jpg");
//				helper.addInline("identifier_logo", res);
//			}
//		});

	}

}
