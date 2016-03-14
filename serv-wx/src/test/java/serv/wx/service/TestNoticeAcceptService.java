package serv.wx.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import serv.wx.service.accept.NoticeAcceptContext;
import serv.wx.service.accept.NoticeAcceptService;
import serv.wx.support.MessageHandlerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-test.xml")
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
	}

}
