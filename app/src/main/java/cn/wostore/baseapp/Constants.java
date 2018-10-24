package cn.wostore.baseapp;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	/**
	 * 请求中
	 */
	public static final int REQUESTING = 1;

	/**
	 * 请求失败
	 */
	public static final int REQ_FAILED = 2;

	/**
	 * 请求成功
	 */
	public static final int REQ_SUCCESS = 3;

	public static final String TYPE_ANDROID = "Android";
	public static final String TYPE_IOS = "ios";
	public static final String TYPE_OTHER = "前端";

	public static final String PARAM_URL = "url";
	public static final String PARAM_TITLE = "title";

	/**
	 * 请求失败
	 */
	public static final String FAIL_RESP = "false";

	/**
	 * 请求成功
	 */
	public static final String SUCCESS_RESP = "true";
	/**
	 * 终端状态 :0全部 1在线 2离线
	 */
	public static final String TERMINAL_STATUS_ONLINE = "1";
	public static final Map<String, String> STATUS_MAP;
	static
	{
		STATUS_MAP = new HashMap<String, String>();
		STATUS_MAP.put("1", "在线");
		STATUS_MAP.put("2", "离线");

	}	/**
	 * 通信通道0、全部 1、2G  2、3G  3、4G  4、卫星 5、北斗
	 */
	public static final Map<String, String> CHANNEL_MAP;
	static
	{
		CHANNEL_MAP = new HashMap<String, String>();
		CHANNEL_MAP.put("1", "2G");
		CHANNEL_MAP.put("2", "3G");
		CHANNEL_MAP.put("3", "4G");
		CHANNEL_MAP.put("4", "卫星");
		CHANNEL_MAP.put("5", "北斗");
	}

}
