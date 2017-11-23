package cn.wostore.baseapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Android手机屏幕尺寸相关的工具类。
 * <p>
 *     用于获取Android手机屏幕信息，例如获取屏幕宽度，获取屏幕高度; 以及进行单位转换，例如dp转px，sp转px，px转dp，px转sp。
 * </p>
 *
 * @author chenxuliang
 * @version 1.0.0
 */
public class DeviceUtil {

    private DeviceUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取手机屏幕宽度，单位为px
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return 手机屏幕宽度，单位为px
     */
    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取手机屏幕高度，单位为px
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return 手机屏幕高度，单位为px
     */
    public static int getScreenHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取手机屏幕密度（density）
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return 手机屏幕密度
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取手机屏幕伸缩密度（scaledDensity），一般用于字体
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return 手机屏幕伸缩密度
     */
    public static float getScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 单位换算方法，dp转px
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @param dpValue 以dp为单位的输入值
     * @return 转换后的值，单位为px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 单位换算方法，sp转px
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @param spValue 以sp为单位的输入值
     * @return 转换后的值，单位为px
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 单位换算方法，px转dp
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @param pxVal   以px为单位的输入值
     * @return 转换后的值，单位为dp
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }


    /**
     * 单位换算方法，px转sp
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @param pxVal   以px为单位的输入值
     * @return 转换后的值，单位为sp
     */
    public static float px2sp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxVal / scale);
    }
}
