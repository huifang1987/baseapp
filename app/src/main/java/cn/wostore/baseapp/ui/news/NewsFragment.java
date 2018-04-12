package cn.wostore.baseapp.ui.news;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import cn.wostore.baseapp.base.BaseFragment;
import cn.wostore.baseapp.widget.CustomToolBar;
import java.util.List;
import java.util.Random;

import cn.wostore.baseapp.R;
import cn.wostore.baseapp.api.response.GetGankResponse;


public class NewsFragment extends BaseFragment<NewsPresenter, NewsModel>
        implements NewsContract.View {

    private static final String TAG = NewsFragment.class.getSimpleName();

    private ProgressDialog mDialog;
    private CustomToolBar toolbar;

    /****************************
     * 覆写 BaseActivity 方法
     **************************/
    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

        mDialog = new ProgressDialog(getActivity());
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCancelable(false);
        mDialog.setMessage("正在加载...");
        setUpToolbar();
        mPresenter.getGank();

    }

    private void setUpToolbar() {
        toolbar = (CustomToolBar) rootView.findViewById(R.id.tool_bar);
        toolbar.setTitle(getString(R.string.news));
        toolbar.setShowBack(false);
    }


    /*************************
     *覆写 View 方法
     ************************/
    @Override
    public void showDialog() {
        mDialog.show();
    }

    @Override
    public void onSucceed(GetGankResponse data) {

        Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();
        List<GetGankResponse.Result> results = data.getResults();

        for (GetGankResponse.Result result : results) {
            Log.d(TAG, result.toString());
        }
    }

    @Override
    public void onFail(String err) {
        Log.e(TAG, err);
        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideDialog() {
        mDialog.dismiss();
    }


}
