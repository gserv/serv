package com.github.gserv.serv.wx.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.gserv.serv.wx.service.accept.NoticeAcceptContext;
import com.github.gserv.serv.wx.service.accept.NoticeAcceptService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.wx.support.MessageHandlerException;

/**
 * 微信入口控制器
 * 
 * @author shiying @ caituo
 * 
 */
public class WxNoticeController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(WxNoticeController.class);

	private NoticeAcceptService noticeAcceptService;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 创建上下文
			NoticeAcceptContext noticeAcceptContext = new NoticeAcceptContext();
			// 获取参数
			noticeAcceptContext.setAccessid(request.getParameter("accessid"));
			noticeAcceptContext.setSign(request.getParameter("sign"));
			noticeAcceptContext.setSignature(request.getParameter("signature"));
			noticeAcceptContext.setTimestamp(request.getParameter("timestamp"));
			noticeAcceptContext.setNonce(request.getParameter("nonce"));
			noticeAcceptContext.setEchostr(request.getParameter("echostr"));
			noticeAcceptContext.setServerBasePath(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
			noticeAcceptContext.setReqbody(IOUtils.toString(request.getInputStream(), "UTF-8"));
			//
			logger.debug("weixin notict accept, context[{}]", JsonUtils.toJson(noticeAcceptContext));
			String resbody = noticeAcceptService.accept(noticeAcceptContext);
			if (resbody == null) {
				return null;
			}
			//
			response.setHeader("Content-type", "text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(resbody);
			out.flush();
			out.close();
			return null;

		} catch (MessageHandlerException e) {
			logger.warn("weixin message handle exception.", e);
			return null;

		}
	}

	public NoticeAcceptService getNoticeAcceptService() {
		return noticeAcceptService;
	}

	public void setNoticeAcceptService(NoticeAcceptService noticeAcceptService) {
		this.noticeAcceptService = noticeAcceptService;
	}

}
