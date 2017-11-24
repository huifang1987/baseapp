package cn.wostore.baseapp.utils;

import android.graphics.Color;

/**
 * @author: Fanghui
 * @mail: fangh18@chinaunicom.cn
 * @date: 2017-11-24.
 */

public class CommonUtil {

	/**
	 * 设置颜色透明度
	 * @param color
	 * @param ratio
	 * @return
	 */
	public static int getColorWithAlpha(int color, float ratio) {
		int newColor = 0;
		int alpha = Math.round(Color.alpha(color) * ratio);
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);
		newColor = Color.argb(alpha, r, g, b);
		return newColor;
	}

}
