package serv.wx.support.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serv.commons.beanvali.DefaultMessageValidateService;
import serv.commons.beanvali.MessageValidateService;
import serv.wx.message.revc.AbstractRevcMessage;
import serv.wx.message.send.AbstractSendMessage;
import serv.wx.service.DefaultTemplateHandler;
import serv.wx.service.accept.NoticeAcceptContext;
import serv.wx.support.MessageHandlerException;
import serv.wx.support.TemplateHandler;
import serv.wx.support.WxMessageHandler;

/**
 * 默认的消息处理器
 * 
 * @author shiying
 *
 */
public class DefaultWxMessageHandler implements WxMessageHandler {
	private static final Logger logger = LoggerFactory.getLogger(DefaultWxMessageHandler.class);
	
	/**
	 * 消息映射表
	 */
	private Map<String, ? extends AbstractSendMessage> messageMap;
	
	/**
	 * 消息验证服务
	 */
	private MessageValidateService messageValidateService = new DefaultMessageValidateService();
	
	/**
	 * 使用表达式
	 */
	private boolean useExpression = false;
	
	/**
	 * 表达式处理器
	 */
	private TemplateHandler templateHandler = new DefaultTemplateHandler();
	
	@Override
	public AbstractSendMessage messageHandler(AbstractRevcMessage revc, NoticeAcceptContext noticeAcceptContext)
			throws MessageHandlerException {
		if (messageMap == null) {
			logger.warn("message handler regex map is null");
			return null;
		}
		for (String regex : messageMap.keySet()) {
			Map<String, Object> redata = useExpression ? new HashMap<String, Object>() : null;
			if (messageValidateService.validate(regex, revc, redata)) {
				if (redata != null) {
					try {
						for (String key : BeanUtils.describe(revc).keySet()) {
							redata.put(key, BeanUtils.getProperty(revc, key));
						}
						for (String key : BeanUtils.describe(noticeAcceptContext).keySet()) {
							redata.put(key, BeanUtils.getProperty(noticeAcceptContext, key));
						}
					} catch (Exception e) {
						logger.warn("dump revc message faild.");
					}
				}
				//
				AbstractSendMessage send = messageMap.get(regex);
				if (send != null) {
					send.setCreateTime(new Date());
					send.setFromUserName(revc.getToUserName());
					send.setToUserName(revc.getFromUserName());
					if (useExpression) {
						send.expressionHandler(redata, templateHandler);
					}
					return send;
				}
			}
		}
		return null;
	}

	public void setMessageMap(Map<String, ? extends AbstractSendMessage> messageMap) {
		this.messageMap = messageMap;
	}

	public void setMessageValidateService(
			MessageValidateService messageValidateService) {
		this.messageValidateService = messageValidateService;
	}

	public void setUseExpression(boolean useExpression) {
		this.useExpression = useExpression;
	}

	public void setTemplateHandler(TemplateHandler templateHandler) {
		this.templateHandler = templateHandler;
	}
	
	
}
