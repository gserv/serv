package serv.commons;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourcesUtils {
	private static final Logger logger = LoggerFactory.getLogger(ResourcesUtils.class);
	
	/**
	 * 通过URL加载字符串
	 * @param url
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String loadStringByUrl(String url, String charset) throws IOException {
		InputStream is = loadResourcesByUrl(url);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String string = null;
		try {
			int b = -1;
			while ((b = is.read()) != -1) {
				baos.write(b);
			}
			string = new String(baos.toByteArray(), charset);
		} finally {
			is.close();
			baos.close();
		}
		return string;
	}
	
	
	/**
	 * 通过URL加载资源，支持http://, file://, classpath://
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static InputStream loadResourcesByUrl(String url) throws IOException {
		if (url.startsWith("file://")) {
			return new FileInputStream(new File(
							url.substring("file://".length(), url.length())));
			
		} else if (url.startsWith("webapp://")) {
			File classPath = new File(URLDecoder.decode(ResourcesUtils.class.getResource("/").getFile(), "UTF-8"));
			File webroot = classPath.getParentFile().getParentFile();
			File file = new File(webroot, url.substring("webapp://".length(), url.length()));
			return loadResourcesByUrl("file://" + file.getAbsolutePath());
			
		} else if (url.startsWith("classpath://")) {
			return ResourcesUtils.class.getClassLoader().getResourceAsStream(
					url.substring("classpath://".length(), url.length()));
			
		} else if (url.startsWith("http://")) {
			URL http = new URL(url);
			URLConnection conn = http.openConnection();
			return conn.getInputStream();
			
		} else {
			logger.warn("not support url : " + url);
			return null;
		}
	}

}
