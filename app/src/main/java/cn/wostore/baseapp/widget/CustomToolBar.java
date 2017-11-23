package cn.wostore.baseapp.widget;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.utils.DeviceUtil;

/**
 * @author chenxuliang
 * @version 1.0.0
 * @date 2017/3/27
 */
public class CustomToolBar extends LinearLayoutCompat {
    /**
     * 状态栏正常模式
     */
    public static int MODE_NORMAL_STATUS_BAR = 0;
    /**
     * 状态栏透明模式
     */
    public static int MODE_TRANSPARENT_STATUS_BAR = 1;

    private LinearLayoutCompat rootView;
    /**
     * 状态栏占位
     */
    private LinearLayout placeholderLl;
    /**
     * 标题
     */
    private TextView titleTv;
    /**
     * toolbar内容区
     */
    private RelativeLayout toolBar;

    /**
     * 返回按钮区域
     */
    private ImageView backActionItem;
    /**
     * 右边按钮区域
     */
    private ImageView rightActionItem;
    /**
     * 右边文字区域
     */
    private TextView rightTextItem;
    /**
     * 模式
     */
    private int mode;
    /**
     * toolbar的高度，不包括状态栏
     */
    private int toolBarHeight;

    private OnCustomToolBarListener mOnCustomToolBarListener;

    public CustomToolBar(Context context) {
        this(context, null);
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mode = MODE_TRANSPARENT_STATUS_BAR;
        toolBarHeight = DeviceUtil.dp2px(context, 53);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_tool_bar, this);
        rootView = (LinearLayoutCompat) findViewById(R.id.root);
        toolBar = (RelativeLayout) findViewById(R.id.tool_bar_rl);
        placeholderLl = (LinearLayout) findViewById(R.id.placeholder_ll);
        titleTv = (TextView) findViewById(R.id.title_tv);
        backActionItem = (ImageView) findViewById(R.id.back_iv);
        rightActionItem = (ImageView) findViewById(R.id.right_action_item_iv);
        rightTextItem = (TextView) findViewById(R.id.right_action_item_tv);
        //设置状态栏高度
        setStatusBarHeight(StatusBarCompat.getStatusBarHeight(this));
        //设置模式
        setMode(mode);
        //设置back按钮
        if (backActionItem != null) {
            backActionItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnCustomToolBarListener != null) {
                        mOnCustomToolBarListener.onBackClick();
                    }
                }
            });
        }
        //设置右边按钮
        if (rightActionItem != null) {
            rightActionItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnCustomToolBarListener != null) {
                        mOnCustomToolBarListener.onRightActionItemClick();
                    }
                }
            });
        }
        //设置右边文字
        if (rightTextItem != null) {
            rightTextItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnCustomToolBarListener != null) {
                        mOnCustomToolBarListener.onRightActionItemClick();
                    }
                }
            });
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        checkHeight();
    }

    public void checkHeight() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                if (mode == MODE_NORMAL_STATUS_BAR) {
                    lp.height = toolBarHeight;
                } else {
                    lp.height = toolBarHeight + StatusBarCompat.getStatusBarHeight(this);
                }
            }
            setLayoutParams(lp);
        }
    }

    /**
     * 设置是否显示右边按钮
     *
     * @param flag
     */
    public void setShowRightActionItem(boolean flag) {
        if (rightActionItem != null) {
            if (flag) {
                rightActionItem.setVisibility(VISIBLE);
            } else {
                rightActionItem.setVisibility(INVISIBLE);
            }
        }
    }

    /**
     * 设置是否显示右边文字
     *
     * @param flag
     */
    public void setShowRightTextItem(boolean flag) {
        if (rightTextItem != null) {
            if (flag) {
                rightTextItem.setVisibility(VISIBLE);
            } else {
                rightTextItem.setVisibility(INVISIBLE);
            }
        }
    }

    /**
     * 设置是否显示back
     *
     * @param flag
     */
    public void setShowBack(boolean flag) {
        if (backActionItem != null) {
            if (flag) {
                backActionItem.setVisibility(VISIBLE);
            } else {
                backActionItem.setVisibility(INVISIBLE);
            }
        }
    }

    /**
     * 设置是否显示title
     *
     * @param flag
     */
    public void setShowTitle(boolean flag) {
        if (titleTv != null) {
            if (flag) {
                titleTv.setVisibility(VISIBLE);
            } else {
                titleTv.setVisibility(GONE);
            }
        }
    }

    /**
     * 设置模式
     *
     * @param mode 0：MODE_NORMAL_STATUS_BAR，一般模式，非透明状态栏模式；1：MODE_TRANSPARENT_STATUS_BAR，透明状态栏模式
     */
    public void setMode(int mode) {
        this.mode = mode;
        if (placeholderLl != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                if (mode == MODE_TRANSPARENT_STATUS_BAR) {
                    placeholderLl.setVisibility(VISIBLE);
                } else {
                    placeholderLl.setVisibility(GONE);
                }
            } else {
                placeholderLl.setVisibility(GONE);
            }
        }
    }

    /**
     * 设置右边文字
     *
     * @param str
     */
    public void setRightText(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (rightTextItem != null) {
            rightTextItem.setText(str);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }

    /**
     * 设置title颜色
     *
     * @param color
     */
    public void setTitleTextColor(int color) {
        if (titleTv != null) {
            titleTv.setTextColor(color);
        }
    }

    /**
     * 设置title字体大小，单位为px
     *
     * @param size
     */
    public void setTitleTextSize(float size) {
        if (titleTv != null) {
            titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    /**
     * 设置toolbar高度
     *
     * @param height
     */
    public void setToolBarHeight(int height) {
        if (toolBar != null && height > 0) {
            ViewGroup.LayoutParams lp = toolBar.getLayoutParams();
            lp.height = height;
            toolBar.setLayoutParams(lp);
        }
    }

    /**
     * 设置statusbar高度
     *
     * @param height
     */
    public void setStatusBarHeight(int height) {
        if (placeholderLl != null && height >= 0) {
            ViewGroup.LayoutParams lp = placeholderLl.getLayoutParams();
            lp.height = height;
            placeholderLl.setLayoutParams(lp);
        }
    }

    /**
     * 设置右边按钮的图片
     *
     * @param resId
     */
    public void setRightActionItemResId(int resId) {
        if (rightActionItem != null) {
            rightActionItem.setImageResource(resId);
        }
    }

    /**
     * 设置左边按钮的图片
     *
     * @param resId
     */
    public void setBackActionItemResId(int resId) {
        if (backActionItem != null) {
            backActionItem.setImageResource(resId);
        }
    }

    public void setElevationValue(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.setElevation(0.0f);
        }
    }

    public void setOnCustomToolBarListener(OnCustomToolBarListener onCustomToolBarListener) {
        this.mOnCustomToolBarListener = onCustomToolBarListener;
    }

    public interface OnCustomToolBarListener {
        void onBackClick();

        void onRightActionItemClick();
    }

}
