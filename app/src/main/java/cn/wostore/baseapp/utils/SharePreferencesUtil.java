package cn.wostore.baseapp.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import cn.wostore.baseapp.app.App;


/**
 * 保存在ShareProferences相关文件名，key 存取本地文件
 *
 * @author Administrator
 */
public final class SharePreferencesUtil {
    private static final String FILE_NAME_CONFIG = "config";
    public static final String KEY_ISLOGGED = "is_logged";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USERID = "userid";

    private SharePreferencesUtil() {
    }

    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareStringData(String fileName, String key, String value) {
        final SharedPreferences sharedPreference =
            App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareBooleanData(String fileName, String key, boolean value) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareIntData(String fileName, String key, int value) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareLongData(String fileName, String key, long value) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 保存数据到本地文件shareproferences
     */
    private static void saveShareFloatData(String fileName, String key, float value) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        final Editor editor = sharedPreference.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 从本地文件
     */
    private static long getShareLongData(String fileName, String key) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getLong(key, 0);
    }

    /**
     * 从本地文件
     */
    private static int getShareIntData(String fileName, String key) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getInt(key, 0);
    }

    /**
     * 从本地文件
     */
    private static String getShareStringData(String fileName, String key) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getString(key, "");
    }

    /**
     * 从本地文件
     */
    private static boolean getShareBooleanData(String fileName, String key, boolean defultValue) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getBoolean(key, defultValue);
    }

    /**
     * 从本地文件
     */
    private static Float getShareFloatData(String fileName, String key, float defultValue) {
        final SharedPreferences sharedPreference =
                App.getContext().getApplicationContext().getSharedPreferences(fileName, 0);
        return sharedPreference.getFloat(key, defultValue);
    }

    /**
     * 判断是否登录账号
     */
    public static boolean isLogged() {
        return getShareBooleanData(FILE_NAME_CONFIG, KEY_ISLOGGED, false);
    }

    /**
     * 设置是否登录账号
     */
    public static void setLogin(boolean logged) {
        saveShareBooleanData(FILE_NAME_CONFIG, KEY_ISLOGGED, logged);
    }
    /**
     * 获取用户名
     */
    public static String getUsername() {
        return getShareStringData(FILE_NAME_CONFIG, KEY_USERNAME);
    }

    /**
     * 保存用户名
     */
    public static void saveUsername(String username) {
        saveShareStringData(FILE_NAME_CONFIG, KEY_USERNAME, username);
    }

    /**
     * 获取用户ID
     */
    public static String getUserID() {
        return getShareStringData(FILE_NAME_CONFIG, KEY_USERID);
    }

    /**
     * 保存用户ID
     */
    public static void saveUserID(String id) {
        saveShareStringData(FILE_NAME_CONFIG, KEY_USERID, id);
    }
    /**
     * 清除用户昵称
     */
    public static void clearUserInfo() {
        setLogin(false);
        saveShareStringData(FILE_NAME_CONFIG, KEY_USERNAME, "");
        saveShareStringData(FILE_NAME_CONFIG, KEY_USERID, "");

    }

}
