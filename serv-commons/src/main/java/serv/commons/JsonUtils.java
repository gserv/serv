package serv.commons;

import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtils {
	
	/**
	 * 获取原始ObjectMapper
	 * @return
	 */
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return mapper;
	}
	
	/**
	 * 解析JSON
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJsonMap(String json) {
		return parseJson(json, Map.class);
	}
	
	/**
	 * 解析JSON
	 * @param json
	 * @param c
	 * @return
	 */
	public static <T> T parseJson(String json, Class<T> c) {
		try {
			return (T) getObjectMapper().readValue(json, c);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析JSON
	 * @param json
	 * @param c
	 * @return
	 */
	public static <T> T parseJson(String json, Class<T> c, Class<?>... parametricType) {
		try {
			ObjectMapper mapper = getObjectMapper();
			JavaType type = mapper.getTypeFactory().constructParametricType(c, parametricType);
			return (T) mapper.readValue(json, type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 转换为JSON
	 * @param o
	 * @return
	 */
	public static String toJson(Object o) {
		try {
			return getObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			e.printStackTrace();
			return "<exception>";
		}
	}

}
