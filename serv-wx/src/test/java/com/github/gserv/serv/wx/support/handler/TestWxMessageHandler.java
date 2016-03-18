package com.github.gserv.serv.wx.support.handler;

import com.github.gserv.serv.wx.message.revc.AbstractRevcMessage;
import com.github.gserv.serv.wx.message.send.AbstractSendMessage;
import com.github.gserv.serv.wx.service.accept.NoticeAcceptContext;
import com.github.gserv.serv.wx.support.MessageHandlerException;
import com.github.gserv.serv.wx.support.WxMessageHandler;
import com.github.gserv.serv.wx.message.send.SendImageMessage;

public class TestWxMessageHandler implements WxMessageHandler {

	@Override
	public AbstractSendMessage messageHandler(AbstractRevcMessage revc, NoticeAcceptContext noticeAcceptContext)
			throws MessageHandlerException {
//		SendTextMessage text = new SendTextMessage();
//		text.setContent("haha");
//		return text;
		SendImageMessage image = new SendImageMessage();
//		image.setImgLocalUrl("file://C:\\Users\\shiying\\Documents\\Tencent Files\\350106052\\Image\\Group\\8G}]ZSIUM}N49FPJ4XRZK1O.jpg");
//		image.setImgLocalUrl("http://mp.weixin.qq.com/wiki/static/assets/ac9be2eafdeb95d50b28fa7cd75bb499.png");
//		image.setImgLocalUrl("webapp://resources/a.jpg");
		image.setImgLocalUrl("classpath://serv/wx/test_image.jpg");
		return image;
	}

}
