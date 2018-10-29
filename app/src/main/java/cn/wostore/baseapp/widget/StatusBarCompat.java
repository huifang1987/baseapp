package cn.wostore.baseapp.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 透明状态栏
 */
public class StatusBarCompat {

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    /**
     * 获取状态栏高度
     *
     * @param view
     * @return
     */
    public static int getStatusBarHeight(View view) {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = view.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = view.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}