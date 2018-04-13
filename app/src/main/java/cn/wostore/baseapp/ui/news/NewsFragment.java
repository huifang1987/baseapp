package cn.wostore.baseapp.ui.news;

import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import butterknife.BindView;
import cn.wostore.baseapp.Constants;
import cn.wostore.baseapp.adapter.ItemListAdapter;
import cn.wostore.baseapp.adapter.ItemListAdapter.OnItemClickListener;
import cn.wostore.baseapp.adapter.ItemListAdapter.OnRetryClickListener;
import cn.wostore.baseapp.app.App;
import cn.wostore.baseapp.base.BaseFragment;
import cn.wostore.baseapp.utils.NetworkUtil;
import cn.wostore.baseapp.utils.ToastUtil;
import cn.wostore.baseapp.widget.CustomToolBar;
import cn.wostore.baseapp.widget.LoadLayout;
import cn.wostore.baseapp.widget.LoadingDialog;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import cn.wostore.baseapp.R;
import cn.wostore.baseapp.api.response.GetGankResponse;


public class NewsFragment extends BaseFragment<NewsPresenter, NewsModel>
        implements NewsContract.View, OnRefreshListener {

    private static final String TAG = NewsFragment.class.getSimpleName();

    @BindView(R.id.tool_bar)
    CustomToolBar toolbar;

    @BindView(R.id.swipe_target)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    @BindView(R.id.layout_load)
    LoadLayout loadLayout;

    private ItemListAdapter adapter;

    private int curPageNum = 1;

    private boolean canLoadMore = false;

    private boolean isFirstIn = true;

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
        setupSwipeToLoadLayout();
        setUpToolbar();
        setUpRecyclerView();
        if (!NetworkUtil.isConnected(App.getContext())) {
            loadLayout.setStatus(LoadLayout.NO_NETWORK);
        } else {
            loadLayout.setStatus(LoadLayout.LOADING);
            mPresenter.getGank(curPageNum);
        }
        //加载布局点击重试操作
        loadLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getGank(curPageNum);
            }
        });
    }

    private void setupSwipeToLoadLayout() {
        swipeToLoadLayout.setLoadMoreEnabled(false);
        swipeToLoadLayout.setOnRefreshListener(this);
    }

    private void setUpToolbar() {
        toolbar.setTitle(getString(R.string.news));
        toolbar.setShowBack(false);
    }

    private void setUpRecyclerView() {
        adapter = new ItemListAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick() {

            }
        });
        adapter.setOnRetryClickListener(new OnRetryClickListener() {
            @Override
            public void onClick() {
                adapter.setRequestStatus(Constants.REQUESTING);
                mPresenter.getGank(curPageNum);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    checkScrollBottom(recyclerView);
                }
            }
        });
    }

    private void refreshData(GetGankResponse response){
        if (!response.isError()){
            if (response.getResults().size() !=0){
                adapter.setRequestStatus(Constants.REQ_SUCCESS);
                if (curPageNum == 1) {
                    adapter.refresh(response.getResults());
                } else {
                    adapter.loadMore(response.getResults());
                }
                canLoadMore = canLoadMore(29, curPageNum);
                if(canLoadMore){
                    adapter.setNextPage(curPageNum + 1);
                    ++curPageNum;
                }else {
                    adapter.setNextPage(-1);
                }
                if (isFirstIn) {
                    isFirstIn = false;
                }
                loadLayout.setStatus(LoadLayout.SUCCESS);
            } else {
                if (isFirstIn){
                    loadLayout.setStatus(LoadLayout.EMPTY);
                }
            }
        } else {
            if (isFirstIn){
                loadLayout.setStatus(LoadLayout.ERROR);
            }
        }

    }

    /**
     * 检查RecyclerView是否滑动到底部
     * @param recyclerView
     */
    private void checkScrollBottom(RecyclerView recyclerView)
    {
        // 滚动到底部
        if (!recyclerView.canScrollVertically(RecyclerView.VERTICAL))
        {
            if (canLoadMore && (adapter.getRequestStatus() != Constants.REQUESTING)){
                adapter.setRequestStatus(Constants.REQUESTING);
                mPresenter.getGank(curPageNum);
            }
        }
    }

    /**
     * 是否可以加载更多
     */
    private boolean canLoadMore(int totalCount, int currentPage) {
        int maxPage = totalCount / 10 + (totalCount % 10 == 0 ? 0 : 1);
        return maxPage <= currentPage ? false : true;
    }

    @Override
    public void onSucceed(GetGankResponse data) {
        if (swipeToLoadLayout != null && swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        refreshData(data);
    }

    @Override
    public void onFail(String err) {
        if (swipeToLoadLayout != null && swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (!isFirstIn){
            adapter.setRequestStatus(Constants.REQ_FAILED);
        }
    }


    @Override
    public void onRefresh() {
        curPageNum = 1;
        mPresenter.getGank(curPageNum);
    }
}
