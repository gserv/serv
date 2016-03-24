package com.github.gserv.serv.wx.service;

import javax.annotation.Resource;

import com.github.gserv.serv.wx.service.accept.NoticeAcceptContext;
import com.github.gserv.serv.wx.service.accept.NoticeAcceptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.gserv.serv.wx.support.MessageHandlerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/com/github/gserv/serv/wx/applicationContext-wx-server-single.xml")
public class TestNoticeAcceptService {
	
	@Resource
	private NoticeAcceptService noticeAcceptService;
	
	@Test
	public void test() throws MessageHandlerException {
		NoticeAcceptContext noticeAcceptContext = new NoticeAcceptContext();
		noticeAcceptContext.setAccessid("wx5641c2474b7e5ab7");
		noticeAcceptContext.setSign("8ef460165dd2e6d9cd14f856b952c7aa");
		noticeAcceptContext.setReqbody(
				"<xml><ToUserName><![CDATA[gh_3e8adccde292]]></ToUserName>"
				+ "<FromUserName><![CDATA[oR5Gjjl_eiZoUpGozMo7dbBJ362A]]></FromUserName>"
				+ "<CreateTime>1394524295</CreateTime>"
				+ "<MsgType><![CDATA[event]]></MsgType>"
				+ "<Event><![CDATA[MASSSENDJOBFINISH]]></Event>"
				+ "<MsgID>1988</MsgID><Status><![CDATA[sendsuccess]]></Status>"
				+ "<TotalCount>100</TotalCount><FilterCount>80</FilterCount>"
				+ "<SentCount>75</SentCount><ErrorCount>5</ErrorCount></xml>");
		String resbody = noticeAcceptService.accept(noticeAcceptContext);
		System.out.println(resbody);
		//

		NoticeAcceptContext noticeAcceptContext3 = new NoticeAcceptContext();
		noticeAcceptContext3.setAccessid("wx5641c2474b7e5ab7");
		noticeAcceptContext3.setSign("8ef460165dd2e6d9cd14f856b952c7aa");
		noticeAcceptContext3.setReqbody(
				"<xml><ToUserName><![CDATA[gh_3e8adccde292]]></ToUserName>"
						+ "<FromUserName><![CDATA[oR5Gjjl_eiZoUpGozMo7dbBJ362A]]></FromUserName>"
						+ "<CreateTime>1394524296</CreateTime>"
						+ "<MsgType><![CDATA[text]]></MsgType>"
						+ "<Content><![CDATA[hello]]></Content>"
						+ "<MsgID>1989</MsgID></xml>");
		String resbody2 = noticeAcceptService.accept(noticeAcceptContext3);
		System.out.println(resbody2);
	}

}
