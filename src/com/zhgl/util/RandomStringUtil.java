package com.zhgl.util;

import java.util.Random;

public class RandomStringUtil {

	private static String digital = "0123456789";
	private static String lowLetter = "abcdefghijklmnopqrstuvwxyz";
	private static String capLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/***
	 * 获取指定长度的随机数
	 * 
	 * @param length
	 *            随机数的长度
	 * @return
	 */
	public static String generatedNumber(int length) {
		return generatedString(length, digital);
	}

	/***
	 * 获取指定长度的随机字母字串
	 * 
	 * @param length
	 *            获取的字母长度
	 * @param model
	 *            MyConstant.LOWERCASE/CAPITAL
	 * @return
	 */
	public static String generatedLetter(int length, int model) {
		String original = capLetter;
		if (model == MyConstant.LOWERCASE) {
			original = capLetter;
		}
		return generatedString(length, original);
	}

	/***
	 * 获取指定长度的随机字串(数字+大小写字母)
	 * 
	 * @param length
	 *            随机字串长度
	 * @return
	 */
	public static String generatedMixture(int length) {
		String original = digital + lowLetter + capLetter;
		return generatedString(length, original);
	}

	/***
	 * 对指定的字串进行随机
	 * 
	 * @param length
	 *            随机字串长度
	 * @param original
	 *            要随机的原始字串
	 * @return
	 */
	public static String generatedString(int length, String original) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(original.length());
			sb.append(original.charAt(number));
		}
		return sb.toString();
	}

}
