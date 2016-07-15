package com.gosuncn.core.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *验证 此类未经过测试
 */
public class VerificationUtil {

	private final static Pattern emailer = Pattern
			.compile("//w+([-+.]//w+)*@//w+([-.]//w+)*//.//w+([-.]//w+)*");
	private static String _codeError;
	// wi =2(n-1)(mod 11)
	final static int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1 };
	// verify digit
	final static int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	private static int[] ai = new int[18];
	private static String[] _areaCode = { "11", "12", "13", "14", "15", "21",
			"22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42",
			"43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
			"63", "64", "65", "71", "81", "82", "91" };
	private static HashMap<String, Integer> dateMap;
	private static HashMap<String, String> areaCodeMap;
	static {
		dateMap = new HashMap<String, Integer>();
		dateMap.put("01", 31);
		dateMap.put("02", null);
		dateMap.put("03", 31);
		dateMap.put("04", 30);
		dateMap.put("05", 31);
		dateMap.put("06", 30);
		dateMap.put("07", 31);
		dateMap.put("08", 31);
		dateMap.put("09", 30);
		dateMap.put("10", 31);
		dateMap.put("11", 30);
		dateMap.put("12", 31);
		areaCodeMap = new HashMap<String, String>();
		for (String code : _areaCode) {
			areaCodeMap.put(code, null);
		}
	}

	/**
	 * 验证手机号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$");
		// p = Pattern.compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证位数
	 * @param code
	 * @return
	 */
	public static boolean verifyLength(String code) {
		int length = code.length();
		if (length == 15 || length == 18) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证省区
	 * @param code
	 * @return
	 */
	public static boolean verifyAreaCode(String code) {
		String areaCode = code.substring(0, 2);
		if (areaCodeMap.containsKey(areaCode)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证生日
	 * @param code
	 * @return
	 */
	public static boolean verifyBirthdayCode(String code) {
		String month = code.substring(10, 12);
		boolean isEighteenCode = (18 == code.length());
		if (!dateMap.containsKey(month)) {
			return false;
		}
		String dayCode = code.substring(12, 14);
		Integer day = dateMap.get(month);
		String yearCode = code.substring(6, 10);
		Integer year = Integer.valueOf(yearCode);

		if (day != null) {
			if (Integer.valueOf(dayCode) > day || Integer.valueOf(dayCode) < 1) {
				return false;
			}
		} else {
			if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
				if (Integer.valueOf(dayCode) > 29
						|| Integer.valueOf(dayCode) < 1) {
					return false;
				}
			} else {
				if (Integer.valueOf(dayCode) > 28
						|| Integer.valueOf(dayCode) < 1) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean containsAllNumber(String code) {
		String str = "";
		if (code.length() == 15) {
			str = code.substring(0, 15);
		} else if (code.length() == 18) {
			str = code.substring(0, 17);
		}
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (!(ch[i] >= '0' && ch[i] <= '9')) {
				return false;
			}
		}
		return true;
	}

	public static boolean verify(String idcard) {
		if (!verifyLength(idcard)) {
			return false;
		}
		if (!containsAllNumber(idcard)) {
			return false;
		}

		String eifhteencard = "";
		if (idcard.length() == 15) {
			eifhteencard = uptoeighteen(idcard);
		} else {
			eifhteencard = idcard;
		}
		if (!verifyAreaCode(eifhteencard)) {
			return false;
		}
		if (!verifyBirthdayCode(eifhteencard)) {
			return false;
		}
		if (!verifyMOD(eifhteencard)) {
			return false;
		}
		return true;
	}

	public static boolean verifyMOD(String code) {
		String verify = code.substring(17, 18);
		if ("x".equals(verify)) {
			code = code.replaceAll("x", "X");
			verify = "X";
		}
		String verifyIndex = getVerify(code);
		if (verify.equals(verifyIndex)) {
			return true;
		}
		// int x=17;
		// if(code.length()==15){
		// x=14;
		// }
		return false;
	}

	public static String getVerify(String eightcardid) {
		int remaining = 0;

		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}

		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
			}

			for (int i = 0; i < 17; i++) {
				sum = sum + wi[i] * ai[i];
			}
			remaining = sum % 11;
		}

		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	public static String uptoeighteen(String fifteencardid) {
		String eightcardid = fifteencardid.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteencardid.substring(6, 15);
		eightcardid = eightcardid + getVerify(eightcardid);
		return eightcardid;
	}
}