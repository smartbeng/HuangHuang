package com.litesuits.common.assist;

import java.security.MessageDigest;

public class SHA1 {
	/**
	 * sha1加密
	 * 
	 * @param text
	 * @return
	 */
	public static String getSHA1(String text) {

		try {

			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			sha.update(text.getBytes("UTF-8"), 0, text.length());
			String ret = byte2hex(sha.digest());

			return ret;
		} catch (Exception exp) {
			exp.printStackTrace();
			return "";
		}
	}

	/**
	 * 将字节数组换成成16进制的字符串
	 * 
	 * @param byteArray
	 * 
	 * @return string
	 */
	private static String byte2hex(byte[] b) {
		//  二行制转字符串   
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}
}
