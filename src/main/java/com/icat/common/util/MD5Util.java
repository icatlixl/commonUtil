package com.icat.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * MD5加密工具包
 * author:icat  
 * blog:https://blog.techauch.com
 */
public class MD5Util {
	/**
	 * MD5加密
	 */
	public static String encrypByMd5(String context) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(context.getBytes());
			byte[] encryContext = md.digest();

			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < encryContext.length; offset++) {
				i = encryContext[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return context;
	}

	/**
	 * 获取UUID
	 */
	public static String generateRandomCode() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
