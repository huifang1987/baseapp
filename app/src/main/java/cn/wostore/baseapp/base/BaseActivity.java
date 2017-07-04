package cn.wostore.baseapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.wostore.baseapp.util.TUtil;

/***************使用例子*********************
1.mvp模式
public class SampleActivity extends BaseActivity<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
    }
}
2.普通模式
public class SampleActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
    }
}
 **********************************/

public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity {

    protected P mPresenter;
    protected M mModel;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(getLayoutId());
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        mContext = this;
        initPresenter();
        initView();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    /******************************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();
}
