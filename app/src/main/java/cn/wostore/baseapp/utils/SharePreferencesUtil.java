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
    private static final String KEY_IS_FIRST_IN = "is_first_in";

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
    public static boolean isFirstIn() {
        return getShareBooleanData(FILE_NAME_CONFIG, KEY_IS_FIRST_IN, true);
    }

    /**
     * 设置是否登录账号
     */
    public static void clearFirstInFlag() {
        saveShareBooleanData(FILE_NAME_CONFIG, KEY_IS_FIRST_IN, false);
    }


}
