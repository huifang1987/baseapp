package cn.wostore.baseapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.wostore.baseapp.rx.RxManager;
import cn.wostore.baseapp.util.TUtil;


/***************使用例子*********************
1.mvp模式
public class SampleFragment extends BaseFragment<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
    @Override
    public int getLayoutResource() {
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
public class SampleFragment extends BaseFragment {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
    }
}
 *************************************/

public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment {
    protected View rootView;
    protected P mPresenter;
    protected M mModel;
    protected RxManager mRxManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        mRxManager=new RxManager();
        mPresenter = TUtil.getT(this, 0);
        mModel= TUtil.getT(this,1);
        initPresenter();
        initView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroyView();
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
