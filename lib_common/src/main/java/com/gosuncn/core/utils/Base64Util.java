package com.gosuncn.core.utils;

import android.util.Base64;

/**
 * Base64加解密
 * @author HWJ
 *
 */
public class Base64Util {
	
	/**
	 * Base64加密
	 * @param str
	 * @return
	 */
	public static String encode(String str){
		String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
		return strBase64;
	}
	
	/**
	 * Base64解密
	 * @param strBase64
	 * @return
	 */
	public static String decode(String strBase64){
		String str=new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT));
		return str;
	}
}
