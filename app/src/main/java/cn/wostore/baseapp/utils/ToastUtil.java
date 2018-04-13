package cn.wostore.baseapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Android Toast工具类。
 * <p>
 * 使用该工具类创建并显示的<code>Toast</code>，同一时刻只会有一个显示，防止多个<code>Toast</code>排队显示。
 * </p>
 */
public class ToastUtil {
    private static Toast sToast;

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 创建并显示一个<code>Toast</code>，使用<code>Toast.LENGTH_SHORT</code>作为持续时间。
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @param str     显示的内容
     */
    public static void showShort(Context context, String str) {
        hideToast();
        sToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        sToast.show();
    }

    /**
     * 创建并显示一个<code>Toast</code>，使用<code>Toast.LENGTH_Long</code>作为持续时间。
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @param str     显示的内容
     */
    public static void showLong(Context context, String str) {
        hideToast();
        sToast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        sToast.show();
    }

    /**
     * 创建并显示一个<code>Toast</code>，可设置持续时间。
     *
     * @param context  上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @param str      显示的内容
     * @param duration <code>Toast</code>持续时间
     */
    public static void showToast(Context context, String str, int duration) {
        hideToast();
        sToast = Toast.makeText(context, str, duration);
        sToast.show();
    }

    /**
     * 隐藏当前<code>Toast</code>
     */
    public static void hideToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
