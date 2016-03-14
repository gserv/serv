package serv.commons;

public class StringUtils {
	
	
	public static void main(String[] arges) {
		System.out.println(propertyNameConvert("v_proview_all_active_result"));
	}
	
	/**
	 * 属性名称转换
	 * aa_bb_cc -> aaBbCc
	 * @param name
	 * @return
	 */
	public static String propertyNameConvert(String name) {
		StringBuffer buf = new StringBuffer(name);
		for (int i=0; i<buf.length(); i++) {
			if (buf.charAt(i) == '_') {
				buf.delete(i, i+1);
				buf.replace(i, i+1, (""+buf.charAt(i)).toUpperCase());
			}
		}
		return buf.toString();
	}

}
