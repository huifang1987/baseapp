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

}
