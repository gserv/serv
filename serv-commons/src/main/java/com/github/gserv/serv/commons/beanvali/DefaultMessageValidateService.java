package com.github.gserv.serv.commons.beanvali;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认的消息验证服务
 * 
 * @author shiying
 *
 */
public class DefaultMessageValidateService implements MessageValidateService {
	private static final Logger logger = LoggerFactory.getLogger(DefaultMessageValidateService.class);

	@Override
	public boolean validate(String regex, Object object)
			throws MessageValidateException {
		return validate(regex, object, null);
	}
	
	@Override
	public boolean validate(String regex, Object object, Map<String, Object> redata)
			throws MessageValidateException {
		try {
			if (regex == null) {
				throw new MessageValidateException("Illegal regex : regex is null");
			}
			if (regex.equals("")) {
				return true;
			}
			List<List<String>> regs = compile(regex);
			logger.debug("regex compile complate, regex [{}], compile [{}]", regex, regs);
			for (List<String> or : regs) {
				boolean andRs = true;
				for (String and : or) {
					if (!validateByRegexItem(and, object, redata)) {
						andRs = false;
						break;
					}
				}
				if (andRs) {
					return true;
				}
			}
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 条件判断实现接口
	 * @author shiying
	 *
	 */
	private static interface Conditional {
		public boolean validate(String propName, String propCondition, String propValue, Object o, Map<String, Object> redata);
	}
	
	/**
	 * 条件检查表
	 */
	private Map<String, Conditional> conditions = new LinkedHashMap<String, Conditional>();
	
	public DefaultMessageValidateService() {
		conditions.put("^=", new Conditional() {
			@Override
			public boolean validate(String propName, String propCondition, String propValue,
					Object o, Map<String, Object> redata) {
				return propValue != null && propValue.startsWith(propCondition);
			}});
		conditions.put("$=", new Conditional() {
			@Override
			public boolean validate(String propName, String propCondition, String propValue,
					Object o, Map<String, Object> redata) {
				return propValue != null && propValue.endsWith(propCondition);
			}});
		conditions.put("!=", new Conditional() {
			@Override
			public boolean validate(String propName, String propCondition, String propValue,
					Object o, Map<String, Object> redata) {
				return propValue != null && !propValue.equals(propCondition);
			}});
		conditions.put("*=", new Conditional() {
			@Override
			public boolean validate(String propName, String propCondition, String propValue,
					Object o, Map<String, Object> redata) {
				return propValue != null && propValue.indexOf(propCondition) != -1;
			}});
		conditions.put("?=", new Conditional() {
			@Override
			public boolean validate(String propName, String propCondition, String propValue,
					Object o, Map<String, Object> redata) {
				Matcher matcher = Pattern.compile(propCondition).matcher(propValue);
				if (!matcher.matches()) {
					return false;
				}
				if (redata != null) {
					for (int i=1; i<=matcher.groupCount(); i++) {	// 0号元素是自身
						redata.put(""+i, matcher.group(i));
					}
				}
				return true;
			}});
		conditions.put("=", new Conditional() {
			@Override
			public boolean validate(String propName, String propCondition, String propValue,
					Object o, Map<String, Object> redata) {
				return propValue != null && propValue.equals(propCondition);
			}});
	}

	
	/**
	 * 验证独立表达式
	 * @param item
	 * @param o
	 * @return
	 */
	private boolean validateByRegexItem(String item, Object o, Map<String, Object> redata) {
		for (String regex : conditions.keySet()) {
			if (item.indexOf(regex) != -1) {
				String propName = item.substring(0, item.indexOf(regex));
				String propCondition = item.substring(item.indexOf(regex)+regex.length(), item.length());
				try {
					String propValue = null;
					try {
						propValue = BeanUtils.getProperty(o, propName);
					} catch (Exception e) {
					}
					logger.debug("hit conditions, propName[{}], propCondition[{}], propValue[{}]", 
							propName, propCondition, propValue);
					return conditions.get(regex).validate(propName, propCondition, propValue, propValue, redata);
				} catch (Exception e) {
					// logger.warn("condition validate faild.", e);
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * 匹配一个字符串的左右字符，比如匹配[]
	 * @param algorithms
	 * @param chaLeft
	 * @param chaRight
	 * @return
	 */
	private boolean checkBracket(String algorithms, char chaLeft, char chaRight) {
		Stack<String> bracket = new Stack<String>();
		for(int i=0; i<algorithms.length(); i++) {
			if (algorithms.charAt(i)==chaLeft){
				bracket.push(""+chaLeft);
			}
			if (algorithms.charAt(i)==chaRight){
				if (bracket.size() > 0){
					bracket.pop();
				} else {
					return false;
				}
			}
		}
		if (bracket.size() > 0){
			return false;
		}
		return true;
	}


	
	/**
	 * 编译表达式，一级列表为OR关系，二级列表AND关系
	 * @param regex
	 * @return
	 */
	private List<List<String>> compile(String regex) throws MessageValidateException {
		List<List<String>> parseRegex = new ArrayList<List<String>>();
		List<String> orRegexArray = Arrays.asList(regex.split("\\s+"));
		// 单纯使用空格分隔是不安全的
		//	例如：[toUserName=to][msgType=text][content=text string]
		// 要先进行预检查
		List<String> orRegexArrayReCheck = new ArrayList<String>();
		String checkTemp = "";
		for (String or : orRegexArray) {
			if (!checkBracket(checkTemp + or, '[', ']')) {
				checkTemp = checkTemp + or;
			} else {
				if (checkTemp.equals("")) {
					orRegexArrayReCheck.add(or);
				} else {
					String str = regex.substring(regex.indexOf(checkTemp), regex.indexOf(or) + or.length());
					orRegexArrayReCheck.add(str);
					checkTemp = "";
				}
			}
		}
		if (!checkTemp.equals("")) {
			throw new MessageValidateException("Illegal regex : " + regex);
		}
		// 
		for (String orRegex : orRegexArrayReCheck) {
			orRegex = orRegex.trim();
			List<String> andList = new ArrayList<String>();
			Matcher matcher = Pattern.compile("\\[?([^\\[\\]]*)\\]?").matcher(orRegex);
			while (matcher.find()) {
				String item = matcher.group(1);
				if (item != null && item.trim().length() > 0) {
					andList.add(item);
				}
			}
			if (andList.size() > 0) {
				parseRegex.add(andList);
			}
		}
		return parseRegex;
	}

	
	
}
