package cn.wostore.baseapp.ui.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.adapter.VideoListAdapter;
import cn.wostore.baseapp.api.ApiEngine;
import cn.wostore.baseapp.api.request.GetVideoListRequest;
import cn.wostore.baseapp.api.response.GetVideoListResponse;
import cn.wostore.baseapp.api.response.GetVideoListResponse.DataBean.VideoBean;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.rx.RxSchedulers;
import cn.wostore.baseapp.utils.L;
import cn.wostore.baseapp.utils.NetUtil;
import cn.wostore.baseapp.utils.SharePreferencesUtil;
import cn.wostore.baseapp.utils.ToastUtil;
import cn.wostore.baseapp.widget.CustomToolBar;
import cn.jzvd.*;
import cn.wostore.baseapp.widget.JZExoPlayer;
import cn.wostore.baseapp.widget.LoadLayout;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static cn.wostore.baseapp.Constants.SUCCESS_RESP;

/**
 * Created by Fanghui at 2018-10-21
 */
public class VideoListActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    CustomToolBar toolbar;

    @BindView(R.id.rv_videolist)
    RecyclerView mRecyclerView;

    @BindView(R.id.load_layout)
    LoadLayout loadLayout;

    VideoListAdapter adapter;

    List<VideoBean> videoList = new ArrayList<>();

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
        setUpLoadlayout();
        setUpRecyclerView();
        Jzvd.setMediaInterface(new JZExoPlayer());
        fetchVideoList();
    }

    private void setUpLoadlayout() {
        loadLayout.setStatus(LoadLayout.LOADING);
        loadLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchVideoList();
            }
        });
    }

    private void fetchVideoList(){
        if (!NetUtil.isConnected(mContext)){
            loadLayout.setStatus(LoadLayout.NO_NETWORK);
            return;
        }
        GetVideoListRequest request = new GetVideoListRequest();
        request.setUserId(SharePreferencesUtil.getUserID());
        ApiEngine.getInstance().getService()
                .getVideoList(request.getRequestBodyMap())
                .compose(RxSchedulers.<GetVideoListResponse>io_main())
                .subscribe(new Observer<GetVideoListResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetVideoListResponse response) {
                        try {
                            if (SUCCESS_RESP.equals(response.getSuccess())){
                                if (response.getData().getList().size() != 0){
                                    videoList.clear();
                                    videoList.addAll(response.getData().getList());
                                    adapter.refresh(videoList);
                                    loadLayout.setVisibility(View.GONE);
                                } else {
                                    loadLayout.setStatus(LoadLayout.EMPTY);
                                }
                            } else {
                                loadLayout.setStatus(LoadLayout.ERROR);
                            }
                        } catch (Exception e) {
                            L.e(e.getLocalizedMessage());
                            loadLayout.setStatus(LoadLayout.ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadLayout.setStatus(LoadLayout.ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void setUpRecyclerView() {
        adapter  = new VideoListAdapter();
        //GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = (Jzvd) view.findViewById(R.id.videoplayer);
                if (jzvd != null && jzvd.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
                    Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
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

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

}
