package cn.wostore.baseapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.BindView;
import cn.wostore.baseapp.Constants;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.manager.AppManager;
import cn.wostore.baseapp.utils.FullScreenWorkaround;
import cn.wostore.baseapp.utils.L;
import cn.wostore.baseapp.utils.NetworkUtil;
import cn.wostore.baseapp.utils.ToastUtil;
import cn.wostore.baseapp.widget.CustomToolBar;
import cn.wostore.baseapp.widget.LoadLayout;


public class WebviewActivity extends BaseActivity {
    private static final String TAG = WebviewActivity.class.getSimpleName();

    @BindView(R.id.tool_bar)
    CustomToolBar mCustomToolBar;

    @BindView(R.id.wv)
	WebView mWebView;

    @BindView(R.id.load_layout)
    LoadLayout loadLayout;

    private String mUrl;

    private String mTitle;

    private WebSettings mWebSettings;
    /**
     * 用于两次点击退出
     */
    private boolean isExit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        FullScreenWorkaround.assistActivity(this);
        mCustomToolBar.setShowBack(false);
        mCustomToolBar.setShowTitle(true);
        mCustomToolBar.setTitle(Constants.BASE_TITLE);
        mCustomToolBar.setOnCustomToolBarListener(new CustomToolBar.OnCustomToolBarListener() {
            @Override
            public void onBackClick() {
                if (!TextUtils.isEmpty(mWebView.getUrl()) && mWebView.getUrl().startsWith("http")) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    }
                }
            }

            @Override
            public void onRightActionItemClick() {

            }
        });

        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);

        mWebSettings.setAppCacheEnabled(true);

        mWebSettings.setDomStorageEnabled(true);

        mWebSettings.setDatabaseEnabled(true);

        mWebSettings.setAllowFileAccess(true);

        mWebSettings.setDefaultTextEncodingName("utf-8");

        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        //解决https页面加载http资源的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        loadLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl(mUrl);
            }
        });

        //测试助手相关
        Intent intent = getIntent();
        String action = intent.getAction();
        mUrl = Constants.BASE_URL;
        if (!TextUtils.isEmpty(mTitle)) {
            mCustomToolBar.setTitle(mTitle);
        } else {
            mCustomToolBar.setTitle(Constants.BASE_TITLE);
        }
        loadUrl(mUrl);
        L.d(TAG, "mUrl:" + mUrl + ",mTitle:" + mTitle);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class MyWebChromeClient extends WebChromeClient{

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)){
                mCustomToolBar.setTitle(title);
            } else {
                mCustomToolBar.setTitle(Constants.BASE_TITLE);
            }
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            L.d(TAG, "onPageStarted,url:" + url);
            loadLayout.setStatus(LoadLayout.LOADING);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadLayout.setStatus(LoadLayout.SUCCESS);
            L.d(TAG, "onPageFinished,url:" + url);
            if (!TextUtils.isEmpty(mWebView.getUrl())
                    && mWebView.getUrl().startsWith("http")) {
                if (mWebView.getUrl().startsWith("https://www.xiaowoxuetang.com/index.html")
                        || mWebView.getUrl().equals("https://www.xiaowoxuetang.com/")) {
                    mCustomToolBar.setShowBack(false);
                } else {
                    mCustomToolBar.setShowBack(true);
                }
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            L.d(TAG, "shouldOverrideUrlLoading");
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
            L.d(TAG, "onReceivedSslError");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            L.d(TAG, "onReceivedError");
        }
    }

    /**
     * 加载h5页面
     */
    private void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            loadLayout.setStatus(LoadLayout.ERROR);
            L.d(TAG, "url is empty");
            return;
        }
        if (!NetworkUtil.isConnected(this)) {
            loadLayout.setStatus(LoadLayout.NO_NETWORK);
            L.d(TAG, "no network");
            return;
        }
        loadLayout.setStatus(LoadLayout.LOADING);
        try {
            mWebView.loadUrl(url);
        } catch (Exception e) {
            loadLayout.setStatus(LoadLayout.ERROR);
            L.d(TAG, "e:" + e);
        }
    }


    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(mWebView.getUrl()) && mWebView.getUrl().startsWith("http")) {
            if (!mWebView.getUrl().startsWith("https://www.xiaowoxuetang.com/index.html")
                    && !mWebView.getUrl().equals("https://www.xiaowoxuetang.com/")){
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return;
                }
            }
        }
        if (!isExit) {
            ToastUtil.showShort(this, getString(R.string.app_quit_toast));
            isExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            AppManager.getAppManager().appExit(WebviewActivity.this, false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //此处必须关闭，解决某些机型崩溃问题
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.destroy();
            mWebView = null;
        }

    }


}
