package serv.web.utils;

import java.util.Arrays;
import java.util.Map;

public class RebuildUrlUtils {
	
	/**
	 * 忽略参数重构URL地址
	 * @param url
	 * @param ignoreParmarName
	 * @return
	 */
	public static String rebuildIgnoreParmars(String url, Map<String, String[]> parmars, String... ignoreParmarName) {
		StringBuffer buf = new StringBuffer();
		buf.append(url);
		if (url.indexOf("?") == -1) {
			buf.append("?");
		} else {
			buf.append("&");
		}
		for (String key : parmars.keySet()) {
			if (Arrays.asList(ignoreParmarName).contains(key)) {
				continue;
			}
			for (String v : parmars.get(key)) {
				buf.append(key);
				buf.append("=");
				buf.append(v);
				buf.append("&");
			}
		}
		buf.delete(buf.length()-1, buf.length());
		return buf.toString();
	}

}
