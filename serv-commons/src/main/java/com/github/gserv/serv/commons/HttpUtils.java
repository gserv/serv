package com.github.gserv.serv.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HTTP静态工具包
 *
 * @author shiying
 *
 */
public class HttpUtils {

	public static final int timeout = 1000 * 60 * 30;

	public static File download(String url, String filebasename) throws IOException {
		File tmpfile = download(url);
		File f = new File(tmpfile.getParentFile(), filebasename);
		tmpfile.renameTo(f);
		return f;
	}

	/**
	 * 下载到指定文件
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	public static void download(String url, File file) throws IOException {
		File tmpfile = download(url);
		org.apache.commons.io.FileUtils.copyFile(tmpfile, file);
	}


	/**
	 * 文件下载
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static File download(String url) throws IOException {
		CloseableHttpClient http = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		get.setConfig(requestConfig);
		try {
			HttpResponse response = http.execute(get);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("Unexpected status code : " + response.getStatusLine().getStatusCode());
			}
			// 获取原始文件名
			String ori_filename = "";
			Header header = response.getFirstHeader("Content-Disposition");
			if (header != null && header.getElements() != null && header.getElements().length > 0) {
				ori_filename = header.getElements()[0].getParameterByName("filename").getValue();
			}
			if (ori_filename.equals("")) {
				ori_filename = url.substring(url.lastIndexOf("/"), url.indexOf("?") == -1 ? url.length() : url.lastIndexOf("?"));
			}
			// 创建临时文件
			File file = FileUtils.createTempFile(ori_filename);
			//
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			InputStream is = response.getEntity().getContent();
			try {
				IOUtils.copyLarge(is, fos);
			} finally {
				fos.flush();
				fos.close();
				is.close();
			}
			return file;

		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			get.releaseConnection();
			http.close();
		}
	}

	/**
	 * 发送POST请求
	 * @param url
	 * @param body
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static String post(String url, String body) throws IOException {
		CloseableHttpClient http = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		post.setConfig(requestConfig);
		try {
			post.addHeader("Content-Type", "text/html;charset=UTF-8");
			post.setEntity(new StringEntity(body, "UTF-8"));
			HttpResponse response = http.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("Unexpected status code : " + response.getStatusLine().getStatusCode()
						+ ", response body [" + IOUtils.toString(response.getEntity().getContent(), "UTF-8") + "] ");
			}
			return IOUtils.toString(response.getEntity().getContent(), "UTF-8");

		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			post.releaseConnection();
			http.close();
		}
	}

	/**
	 * 发送POST请求
	 * @param url
	 * @param requestEntity
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, File> filePart, Map<String, String> parmars) throws IOException {
		CloseableHttpClient http = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		post.setConfig(requestConfig);
		try {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			for (int i=0; i<filePart.keySet().size(); i++) {
				String key = filePart.keySet().toArray(new String[0])[i];
				builder.addPart(key, new FileBody(filePart.get(key)));
			}
			for (int i=0; i<parmars.keySet().size(); i++) {
				String key = parmars.keySet().toArray(new String[0])[i];
				builder.addTextBody(key, parmars.get(key));
			}
			post.setEntity(builder.build());
			HttpResponse response = http.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("Unexpected status code : " + response.getStatusLine().getStatusCode()
						+ ", response body [" + IOUtils.toString(response.getEntity().getContent(), "UTF-8") + "] ");
			}
			return IOUtils.toString(response.getEntity().getContent(), "UTF-8");

		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			post.releaseConnection();
			http.close();
		}
	}

	/**
	 * 发送POST请求
	 * @param url
	 * @param parmars
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> parmars) throws IOException {
		CloseableHttpClient http = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		post.setConfig(requestConfig);
		try {
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			for (int i=0; i<parmars.keySet().size(); i++) {
				String key = parmars.keySet().toArray(new String[0])[i];
				nvps.add(new BasicNameValuePair(key, parmars.get(key)));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			HttpResponse response = http.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("Unexpected status code : " + response.getStatusLine().getStatusCode()
						+ ", response body [" + IOUtils.toString(response.getEntity().getContent(), "UTF-8") + "] ");
			}
			return IOUtils.toString(response.getEntity().getContent(), "UTF-8");

		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			post.releaseConnection();
			http.close();
		}
	}

	/**
	 * 发送GET请求
	 * @param url
	 * @param parmars
	 * @return
	 * @throws IOException
	 */
	public static String get(String url, Map<String, String> parmars) throws IOException {
		CloseableHttpClient http = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		get.setConfig(requestConfig);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parmars != null) {
				for (int i=0; i<parmars.keySet().size(); i++) {
					String key = parmars.keySet().toArray(new String[0])[i];
					nameValuePairs.add(new BasicNameValuePair(key, parmars.get(key)));
				}
			}
            String stringParmars = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs));  
            if (stringParmars.length() > 0) {
                url = url.indexOf("?") == -1 ? (url + "?" + stringParmars) : (url + ":" + stringParmars);
            }
            get.setURI(new URI(url));
			HttpResponse response = http.execute(get);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("Unexpected status code : " + response.getStatusLine().getStatusCode() 
						+ ", response body [" + IOUtils.toString(response.getEntity().getContent(), "UTF-8") + "] ");
			}
			return IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			
		} catch (IOException e) {
			throw new IOException(e);
		} catch (URISyntaxException e) {
			throw new IOException(e);
		} finally {
			get.releaseConnection();
			http.close();
		}
	}

}
