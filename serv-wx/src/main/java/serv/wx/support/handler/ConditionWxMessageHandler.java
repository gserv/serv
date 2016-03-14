package serv.wx.support.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import serv.commons.JsonUtils;
import serv.commons.beanvali.DefaultMessageValidateService;
import serv.commons.beanvali.MessageValidateService;
import serv.wx.message.revc.AbstractRevcMessage;
import serv.wx.message.send.AbstractSendMessage;
import serv.wx.service.accept.NoticeAcceptContext;
import serv.wx.support.MessageHandlerException;
import serv.wx.support.WxMessageHandler;

/**
 * 条件选择处理器
 * 
 * 如果include和exclude都为空，全部过滤
 * 如果include或exclude只有一个不为空，以不为空为准
 * 如果include和exclude都不为空，exclude优先级 > include优先级
 * 
 * @author shiying
 *
 */
public class ConditionWxMessageHandler implements WxMessageHandler {
	private static final Logger logger = LoggerFactory.getLogger(ConditionWxMessageHandler.class);
	
	/**
	 * 包含
	 */
	private List<String> include;
	
	/**
	 * 排除
	 */
	private List<String> exclude;
	
	/**
	 * 处理器
	 */
	private WxMessageHandler handler;
	
	/**
	 * 消息验证
	 */
	private MessageValidateService messageValidateService = new DefaultMessageValidateService();
	
	@PostConstruct
	public void init() {
		if (include != null) {
			List<String> _include = include;
			include = new ArrayList<String>();
			for (String in : _include) {
				if (in != null && !in.trim().equals("")) {
					include.add(in);
				}
			}
		}
		if (exclude != null) {
			List<String> _exclude = exclude;
			exclude = new ArrayList<String>();
			for (String ex : _exclude) {
				if (ex != null && !ex.trim().equals("")) {
					exclude.add(ex);
				}
			}
		}
	}
	
	/**
	 * 判断条件列表
	 * @param list
	 * @param message
	 * @param or 为true则为or关系，否则为and
	 * @return
	 */
	private Boolean ruleMatcher(List<String> list, AbstractRevcMessage message, boolean or) {
		if (list == null || list.size() <= 0) return null;
		if (or) {
			// or
			for (String regex : list) {
				if (messageValidateService.validate(regex, message)) {
					return true;
				}
			}
			return false;
			
		} else {
			// and
			for (String regex : list) {
				if (!messageValidateService.validate(regex, message)) {
					return false;
				}
			}
			return true;
			
		}
	}

	@Override
	public AbstractSendMessage messageHandler(AbstractRevcMessage revc, NoticeAcceptContext noticeAcceptContext)
			throws MessageHandlerException {
		if (handler == null) {
			logger.warn("condition handler is null");
			return null;
		}
		Boolean in = ruleMatcher(include, revc, true);
		Boolean ex = ruleMatcher(exclude, revc, true);
		if (in == null && ex == null) {
			return handler.messageHandler(revc, noticeAcceptContext);
		} else if (in == null && ex != null) {
			if (!ex) {
				return handler.messageHandler(revc, noticeAcceptContext);
			}
		} else if (in != null && ex == null) {
			if (in) {
				return handler.messageHandler(revc, noticeAcceptContext);
			}
		} else {
			if (in && !ex) {
				return handler.messageHandler(revc, noticeAcceptContext);
			}
		}
		return null;
	}

	public List<String> getInclude() {
		return include;
	}

	public void setInclude(List<String> include) {
		this.include = include;
	}

	public List<String> getExclude() {
		return exclude;
	}

	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}

	public WxMessageHandler getHandler() {
		return handler;
	}

	public void setHandler(WxMessageHandler handler) {
		this.handler = handler;
	}

	public MessageValidateService getMessageValidateService() {
		return messageValidateService;
	}

	public void setMessageValidateService(
			MessageValidateService messageValidateService) {
		this.messageValidateService = messageValidateService;
	}
	
	

}
