package serv.wx.support.handler;

import java.util.Arrays;

import org.junit.Test;

import serv.wx.message.MessageType;
import serv.wx.message.revc.AbstractRevcMessage;
import serv.wx.message.revc.RevcTextMessage;
import serv.wx.message.send.AbstractSendMessage;
import serv.wx.service.accept.NoticeAcceptContext;
import serv.wx.support.MessageHandlerException;
import serv.wx.support.WxMessageHandler;

public class TestConditionWxMessageHandler {
	
	@Test
	public void test() throws MessageHandlerException {
		ConditionWxMessageHandler condition = new ConditionWxMessageHandler();
		condition.setInclude(Arrays.asList(new String[] {
			//	"[msgType=voice]",
			//	"[msgType=text]"
		}));
		condition.setExclude(Arrays.asList(new String[] {
			//	"[msgType=text]"
		}));
		condition.setHandler(new WxMessageHandler() {
			@Override
			public AbstractSendMessage messageHandler(AbstractRevcMessage revc, NoticeAcceptContext noticeAcceptContext)
					throws MessageHandlerException {
				System.out.println("pass");
				return null;
			}
		});
		RevcTextMessage revc = new RevcTextMessage();
		revc.setContent("content");
		revc.setMsgType(MessageType.text);
		condition.messageHandler(revc, new NoticeAcceptContext());
	}

}
