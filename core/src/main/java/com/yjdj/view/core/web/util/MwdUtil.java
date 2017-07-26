package com.yjdj.view.core.web.util;

import java.util.Random;

public class MwdUtil {
	/**
	 * 获取随机颜色
	 * @param length;
	 * @return
	 */
	public static String getRandomColor() {
		String str = "abcdef0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer("#");

		for (int i = 0; i < 6; i++) {
			buf.append(str.charAt(random.nextInt(16)));
		}
		return buf.toString();
	}
}
