package cn.wostore.baseapp.ui.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.app.App;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.ui.setting.SettingActivity;
import cn.wostore.baseapp.ui.video.VideoListActivity;
import cn.wostore.baseapp.utils.SharePreferencesUtil;
import cn.wostore.baseapp.utils.ToastUtil;
import cn.wostore.baseapp.widget.CustomToolBar;

/**
 * Created by Fanghui at 2018-10-21
 */
public class MapActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout layout;

    @BindView(R.id.tool_bar)
    CustomToolBar toolbar;

    @BindView(R.id.username)
    TextView userNameTv;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setUpToolbar();
        String username = SharePreferencesUtil.getNickname();
        if (!TextUtils.isEmpty(username)){
            userNameTv.setText(username);
        }

    }

    private void setUpToolbar() {
        toolbar.setTitle(getString(R.string.title_map));
        toolbar.setShowBack(true);
        toolbar.setBackActionItemResId(R.mipmap.icon_mine);
        toolbar.setOnCustomToolBarListener(new CustomToolBar.OnCustomToolBarListener() {
            @Override
            public void onBackClick() {
                layout.openDrawer(GravityCompat.START);
            }

            @Override
            public void onRightActionItemClick() {

            }
        });
    }

    @OnClick({R.id.ll_video, R.id.ll_setting})
    public void clickMenu(View view){
        switch (view.getId()){
            case R.id.ll_video:
                VideoListActivity.launch(this);
                break;
            case R.id.ll_setting:
                SettingActivity.launch(this);
                break;
        }
    }
}
