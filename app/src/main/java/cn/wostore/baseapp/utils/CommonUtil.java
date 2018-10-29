package cn.wostore.baseapp.utils;

import static cn.wostore.baseapp.api.ApiService.BASE_URL;

/**
 * @author: Fanghui
 * @mail: fangh18@chinaunicom.cn
 * @date: 2017-11-24.
 */

public class CommonUtil {

	public static String getImageUrl(String id){
		return String.format(BASE_URL+"common/image/view/"+id);
	}

	public static String getVideoUrl(String id){
		return String.format(BASE_URL+id);
	}

}
