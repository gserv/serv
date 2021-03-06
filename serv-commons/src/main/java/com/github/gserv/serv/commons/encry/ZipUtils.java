package com.github.gserv.serv.commons.encry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	
//	public static void main(String[] arges) {
//		String str = "UEsDBBQACAgIAIVqvEYAAAAAAAAAAAAAAAABAAAA";
//		System.out.println(str);
//		String zip = compressForString(str);
//		System.out.println(zip);
//		String str2 = decompressAsString(zip);
//		System.out.println(str2);
//		System.out.println(Base64Util.encodeForString(str));
//	}
	
	
	
	/**
	 * 压缩字符串
	 * @param str
	 * @return
	 */
	public static final String compressForString(String str) {
		return Base64Util.encode(compress(str));
	}
    
    /**
     * 解压字符串
     * @param str
     * @return
     */
    public static String decompressAsString(String str) {
		return decompress(Base64Util.decode(str));
    }
	
	
	
	
	/**
	 * 压缩字符串为 byte[] 储存可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)方法
	 * 保存为字符串
	 *
	 * @param str
	 *            压缩前的文本
	 * @return
	 */
	public static final byte[] compress(String str) {
		if (str == null)
			return null;

		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return compressed;
	}
	
	
	

	/**
	 * 将压缩后的 byte[] 数据解压缩
	 *
	 * @param compressed
	 *            压缩后的 byte[] 数据
	 * @return 解压后的字符串
	 */
	public static final String decompress(byte[] compressed) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}
	
	
	
	
	
	
	
	
}
