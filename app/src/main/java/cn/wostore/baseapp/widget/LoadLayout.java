package cn.wostore.baseapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.app.App;

/**
 * 加载布局
 * Created by hhq on 2017/4/10.
 */

public class LoadLayout extends RelativeLayout {

    /**
     * 加载中
     */
    public static final int LOADING = 1;
    /**
     * 无网络
     */
    public static final int NO_NETWORK = 2;
    /**
     * 加载失败
     */
    public static final int ERROR = 3;
    /**
     * 加载完成
     */
    public static final int SUCCESS = 4;
    /**
     * 加载为空
     */
    public static final int EMPTY = 5;
    /**
     * 当前模式
     */
    private int status;
    /**
     * 加载图区域
     */
    private RelativeLayout contentLlyt;
    /**
     * 加载图片
     */
    private ImageView loadIv;
    /**
     * 加载结果图片
     */
    private ImageView finalIv;

    /**
     * 底部占位
     */
    private RelativeLayout bottomRl;

    private boolean emptyClickEnabled;

    private AnimationDrawable mAnimDrawable;

    private LoadCallbackInterface loadCallbackInterface;

    private OnClickListener onRetryClickListener;

    public LoadLayout(Context context) {
        this(context, null);
    }

    public LoadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadLayout);
        status = ta.getInteger(R.styleable.LoadLayout_status, LOADING);
        ta.recycle();
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_load, this);
        contentLlyt = (RelativeLayout) findViewById(R.id.load_content);
        loadIv = (ImageView) findViewById(R.id.load_img);
        finalIv = (ImageView) findViewById(R.id.final_img);
        bottomRl = (RelativeLayout) findViewById(R.id.bottom);
        initSetAdjustmentHeight();
    }

    /**
     * 微调使居中
     */
    private void initSetAdjustmentHeight() {
        int height =
                (int) getResources().getDimension(R.dimen.toolbar_height) + StatusBarCompat.getStatusBarHeight(getContext());
        setAdjustmentDistance(height);
    }

    /**
     * 设置微调距离
     */
    public void setAdjustmentDistance(int distance) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bottomRl.getLayoutParams();
        lp.height = distance;
    }

    /**
     * 设置是否居中显示
     */
    public void setCenterInParent(boolean flag) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) contentLlyt.getLayoutParams();
        if (flag) {
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }
    }

    /**
     * 设置topMargin
     */
    public void setTopMargin(int topMargin) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) contentLlyt.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.topMargin = topMargin;
    }

    /**
     * 获取当前模式
     */
    public int getStatus() {
        return status;
    }

    /**
     * 根据不同的模式加载不同的布局
     */
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADING:
                    if (null != loadCallbackInterface) {
                        loadCallbackInterface.callBack(VISIBLE);
                    }
                    contentLlyt.setClickable(false);
                    Glide.with(App.getContext()).load(R.mipmap.loading1).into(loadIv);
                    loadIv.setVisibility(VISIBLE);
                    finalIv.setVisibility(GONE);
                    setVisibility(VISIBLE);
                    break;
                case NO_NETWORK:
                    if (null != loadCallbackInterface) {
                        loadCallbackInterface.callBack(VISIBLE);
                    }
                    finalIv.setImageResource(R.mipmap.tips_no_network);
                    loadIv.setVisibility(GONE);
                    finalIv.setVisibility(VISIBLE);
                    if (null != onRetryClickListener) {
                        contentLlyt.setOnClickListener(onRetryClickListener);
                    }
                    contentLlyt.setClickable(true);
                    setVisibility(VISIBLE);
                    break;
                case ERROR:
                    if (null != loadCallbackInterface) {
                        loadCallbackInterface.callBack(VISIBLE);
                    }
                    finalIv.setImageResource(R.mipmap.tips_error);
                    loadIv.setVisibility(GONE);
                    finalIv.setVisibility(VISIBLE);
                    if (null != onRetryClickListener) {
                        contentLlyt.setOnClickListener(onRetryClickListener);
                    }
                    contentLlyt.setClickable(true);
                    setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    if (null != loadCallbackInterface) {
                        loadCallbackInterface.callBack(GONE);
                    }
                    loadIv.setVisibility(GONE);
                    finalIv.setVisibility(GONE);
                    contentLlyt.setClickable(false);
                    setVisibility(GONE);
                    break;
                case EMPTY:
                    if (null != loadCallbackInterface) {
                        loadCallbackInterface.callBack(VISIBLE);
                    }
                    if (null != onRetryClickListener) {
                        contentLlyt.setOnClickListener(onRetryClickListener);
                    }
                    contentLlyt.setClickable(emptyClickEnabled);
                    finalIv.setImageResource(R.mipmap.tips_empty);
                    loadIv.setVisibility(GONE);
                    finalIv.setVisibility(VISIBLE);
                    setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置更改模式
     */
    public void setStatus(int status) {
        this.status = status;
        uiHandler.sendEmptyMessage(status);
    }

    /**
     * 加载布局显示和隐藏的回调，方便控制其它布局的显示和隐藏
     */
    public interface LoadCallbackInterface {
        void callBack(int loadVisibility);
    }

    public OnClickListener getOnRetryClickListener() {
        return onRetryClickListener;
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }

    public void clearLoadCallbackInterface() {
        loadCallbackInterface = null;
    }

    public void setLoadCallbackInterface(LoadCallbackInterface loadCallbackInterface) {
        this.loadCallbackInterface = loadCallbackInterface;
    }

    public void setEmptyClickEnabled(boolean emptyClickEnabled) {
        this.emptyClickEnabled = emptyClickEnabled;
    }
}
