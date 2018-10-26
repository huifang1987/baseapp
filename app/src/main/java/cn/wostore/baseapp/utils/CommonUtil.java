package cn.wostore.baseapp.utils;

import android.graphics.Color;

import static cn.wostore.baseapp.api.ApiService.BASE_URL;

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

	public static String getImageUrl(String id){
		return String.format(BASE_URL+"common/image/view/"+id);
	}

	public static String getVideoUrl(String id){
		return String.format(BASE_URL+id);
	}

}
