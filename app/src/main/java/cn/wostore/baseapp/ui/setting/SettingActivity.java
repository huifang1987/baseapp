package cn.wostore.baseapp.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.app.App;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.manager.AppManager;
import cn.wostore.baseapp.ui.login.LoginActivity;
import cn.wostore.baseapp.utils.DataCleanManager;
import cn.wostore.baseapp.utils.SharePreferencesUtil;
import cn.wostore.baseapp.utils.ToastUtil;
import cn.wostore.baseapp.widget.CustomToolBar;

/**
 * Created by Fanghui at 2018-10-21
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    CustomToolBar toolbar;

    @BindView(R.id.tv_cache_size)
    TextView cacheSizeTv;

    private String cacheSize = "0 KB";

    public static void launch(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setUpToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCacheSize();
    }

    private void setUpToolbar() {
        toolbar.setTitle(getString(R.string.settings));
        toolbar.setShowBack(true);
        toolbar.setOnCustomToolBarListener(new CustomToolBar.OnCustomToolBarListener() {
            @Override
            public void onBackClick() {
                finish();
            }

            @Override
            public void onRightActionItemClick() {

            }
        });
    }

    @OnClick({R.id.ll_clear_cache, R.id.tv_logout})
    public void clickMenu(View view){
        switch (view.getId()){
            case R.id.ll_clear_cache:
                clearCache();
                break;
            case R.id.tv_logout:
                logout();
                break;
        }
    }

    private void clearCache(){
        DataCleanManager.clearAllCache(mContext);
        cacheSizeTv.setText("0 KB");
        ToastUtil.showShort(this, App.getContext().getString(R.string.clear_cache_ok) );
    }

    private void logout(){
        SharePreferencesUtil.clearUserInfo();
        LoginActivity.launch(this);
        AppManager.getAppManager().finishAllActivity();
    }

    public void getCacheSize() {
        try {
            cacheSize = DataCleanManager.getTotalCacheSize(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(cacheSize)){
            cacheSizeTv.setText(cacheSize);
        }
    }
}
