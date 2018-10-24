package cn.wostore.baseapp.utils;

import android.util.Log;

/**
 * Android Log统一管理类。
 * <p>
 * 用于控制控制台log的打印。
 * </p>
 * <p>
 * 默认打印log，且TAG为"wostore"。
 * </p>
 * <p>
 * 关闭log打印，使用
 * <pre>
 * L.setIsDebug(false);
 * </pre>
 * 打开log打印，使用
 * <pre>
 * L.setIsDebuge(true);
 * </pre>
 * 设置默认TAG，使用
 * <pre>
 * L.setTAG("TAG");
 * </pre>
 * </p>
 *
 * @author chenxuliang
 * @version 1.0.0
 */
public class L {

    /**
     * 是否打印log
     */
    private static boolean isDebug = true;
    /**
     * 打印log的
     */
    private static String TAG = "yunweitong";

    /**
     * 设置是否打印log
     *
     * @param isDebug true：打印Log，false:不打印Log
     */
    public static void setIsDebug(boolean isDebug) {
        L.isDebug = isDebug;
    }

    /**
     * 获取是否打印log
     *
     * @return true表示打印log，false表示不打印
     */
    public static boolean isDebug() {
        return isDebug;
    }

    /**
     * 设置打印log的TAG
     *
     * @param TAG 表示TAG的字符串
     */
    public static void setTAG(String TAG) {
        L.TAG = TAG;
    }

    /**
     * 获取打印log的TAG
     *
     * @return 打印log的TAG
     */
    public static String getTAG() {
        return TAG;
    }

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 打印INFO级别的log信息，使用设置的TAG
     *
     * @param msg log信息
     */
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    /**
     * 打印DEBUG级别的log信息，使用设置的TAG
     *
     * @param msg log信息
     */
    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    /**
     * 打印ERROR级别的log信息，使用设置的TAG
     *
     * @param msg log信息
     */
    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    /**
     * 打印VERBOSE级别的log信息，使用设置的TAG
     *
     * @param msg log信息
     */
    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    /**
     * 打印INFO级别的log信息，使用传入的类名作为TAG
     *
     * @param clazz 类名
     * @param msg   log信息
     */
    public static void i(Class<?> clazz, String msg) {
        if (isDebug)
            Log.i(clazz.getName(), msg);
    }

    /**
     * 打印DEBUG级别的log信息，使用传入的类名作为TAG
     *
     * @param clazz 类名
     * @param msg   log信息
     */
    public static void d(Class<?> clazz, String msg) {
        if (isDebug)
            Log.d(clazz.getName(), msg);
    }

    /**
     * 打印ERROR级别的log信息，使用传入的类名作为TAG
     *
     * @param clazz 类名
     * @param msg   log信息
     */
    public static void e(Class<?> clazz, String msg) {
        if (isDebug)
            Log.e(clazz.getName(), msg);
    }

    /**
     * 打印VERBOSE级别的log信息，使用传入的类名作为TAG
     *
     * @param clazz 类名
     * @param msg   log信息
     */
    public static void v(Class<?> clazz, String msg) {
        if (isDebug)
            Log.v(clazz.getName(), msg);
    }

    /**
     * 打印INFO级别的log信息，使用自定义的TAG
     *
     * @param tag TAG字符串
     * @param msg Log信息
     */
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    /**
     * 打印DEBUG级别的log信息，使用自定义的TAG
     *
     * @param tag TAG字符串
     * @param msg Log信息
     */
    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    /**
     * 打印ERROR级别的log信息，使用自定义的TAG
     *
     * @param tag TAG字符串
     * @param msg Log信息
     */
    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    /**
     * 打印VERBOSE级别的log信息，使用自定义的TAG
     *
     * @param tag TAG字符串
     * @param msg Log信息
     */
    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }
}
