package com.github.gserv.serv.wx.message;

import com.github.gserv.serv.wx.message.revc.AbstractRevcMessage;
import org.junit.Test;

public class TestMessageParse {
	
	@Test
	public void testParse() {
		String xml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
				+ "<CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content>"
				+ "<MsgId>1234567890123456</MsgId></xml>";
		AbstractRevcMessage message = AbstractRevcMessage.parseWxRevcXml(xml);
		System.out.println(message.getClass());
		System.out.println(message.toWxXml());
	}

}
