package serv.wx.support;

import java.util.Map;

/**
 * 模板处理器接口
 * 
 * @author shiying
 *
 */
public interface TemplateHandler {
	
	/**
	 * 模板处理器
	 * @param template
	 * @param data
	 * @return
	 */
	public String handler(String template, Map<String, Object> data);

}
