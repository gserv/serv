package serv.commons.encry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Stack;

/**
 * Hash加密方法工具包
 * 
 * @author shiying
 *
 */
public class HashUtils {
	
	private static String getMd5ByFile(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (in == null) {
			return "<error>";
		}
		try {
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer.toString().getBytes("UTF-8"));
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != in) {
			    try {
				    in.close();
				} catch (IOException e) {
				    e.printStackTrace();
				}
			}
		}
		return value;
	}
	
	
	/**
	 * 获得文件的MD5
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String md5(File file) {
		if (!file.exists()) {
			return null;
		}
		if (!file.isDirectory()) {
			return getMd5ByFile(file);
		}
		StringBuffer buf = new StringBuffer();
		Stack<File> stack = new Stack<File>();
		stack.push(file);
		while (!stack.empty()) {
			File f = stack.pop();
			if (f.isFile()) {
				buf.append(getMd5ByFile(f));
			} else {
				for (File s : f.listFiles()) {
					stack.push(s);
				}
			}
		}
		return md5(buf.toString());
	}
	

	/**
	 * 获得字符串的MD5
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		byte[] md5Bytes = new byte[0];
		try {
			md5Bytes = md5.digest(str.getBytes("UTF-8"));
			
			StringBuffer hexValue = new StringBuffer();

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			
			
			return hexValue.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * SHA方法
	 * @param value
	 * @return
	 */
	public static String sha1(String value) {
		try {
			return hash(MessageDigest.getInstance("SHA1"), value);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String hash(MessageDigest digest, String src) {
		return toHexString(digest.digest(src.getBytes()));
	}

	private static String toHexString(byte[] bytes) {
		char[] values = new char[bytes.length * 2];
		int i = 0;
		for (byte b : bytes) {
			values[i++] = LETTERS[((b & 0xF0) >>> 4)];
			values[i++] = LETTERS[b & 0xF];
		}
		return String.valueOf(values);
	}

	private static final char[] LETTERS = "0123456789ABCDEF".toCharArray();

}
