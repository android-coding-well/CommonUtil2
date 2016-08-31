package com.gosuncn.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串辅助工具类
 * 
 * @author HWJ
 * 
 */
public class StringUtil {

	/**
	 * 判断字符串是否含有中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}
	
	/**
	 * 判断是否包含表情符号
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		if (source==null) {
			return false;
		}
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
}
