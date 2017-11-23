package cn.wostore.baseapp.ui.fragment;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.wostore.baseapp.base.BaseFragment;
import cn.wostore.baseapp.widget.CustomToolBar;
import java.util.List;
import java.util.Random;

import cn.wostore.baseapp.R;
import cn.wostore.baseapp.api.response.GetGankResponse;
import cn.wostore.baseapp.mvp.contract.HomeContract;
import cn.wostore.baseapp.mvp.model.HomeModel;
import cn.wostore.baseapp.mvp.presenter.HomePresenter;


public class HomeFragment extends BaseFragment<HomePresenter, HomeModel>
        implements HomeContract.View {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private ProgressDialog mDialog;
    private FloatingActionButton mFab;
    private TextView mTextView;
    private CustomToolBar toolbar;

    /****************************
     * 覆写 BaseActivity 方法
     **************************/
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

        mTextView = (TextView) rootView.findViewById(R.id.tv);
        mFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        toolbar = (CustomToolBar) rootView.findViewById(R.id.tool_bar);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCancelable(false);
        mDialog.setMessage("正在加载...");
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getGank();
            }
        });
        setUpToolbar();
    }

    private void setUpToolbar() {
        toolbar.setTitle(getString(R.string.title_home));
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
        mTextView.setText(results.get(new Random().nextInt(10)).toString());

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
