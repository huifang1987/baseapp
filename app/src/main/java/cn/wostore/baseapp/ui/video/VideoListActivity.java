package cn.wostore.baseapp.ui.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.widget.CustomToolBar;

/**
 * Created by Fanghui at 2018-10-21
 */
public class VideoListActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    CustomToolBar toolbar;


    public static void launch(Context context) {
        Intent intent = new Intent(context, VideoListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setUpToolbar();
    }

    private void setUpToolbar() {
        toolbar.setTitle(getString(R.string.video));
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
}
