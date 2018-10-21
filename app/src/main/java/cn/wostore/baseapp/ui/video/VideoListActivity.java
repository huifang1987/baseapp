package cn.wostore.baseapp.ui.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.adapter.VideoListAdapter;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.widget.CustomToolBar;
import cn.jzvd.*;

/**
 * Created by Fanghui at 2018-10-21
 */
public class VideoListActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    CustomToolBar toolbar;

    @BindView(R.id.rv_videolist)
    RecyclerView mRecyclerView;

    VideoListAdapter adapter;

    List<VideoObject> videoList = new ArrayList<>();

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
        setUpRecyclerView();
        fetchVideoList();
    }

    private void fetchVideoList(){
        VideoObject video_1 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        VideoObject video_2 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        VideoObject video_3 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        VideoObject video_4 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        VideoObject video_5 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        videoList.add(video_1);
        videoList.add(video_2);
        videoList.add(video_3);
        videoList.add(video_4);
        videoList.add(video_5);
        VideoObject video_6 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        VideoObject video_7 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        VideoObject video_8 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        VideoObject video_9 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        VideoObject video_10 = new VideoObject(
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/video/3384beaa6def443592bf4c62614346c4.mp4?st=0f54OpPlWZtqdJpS7sPzIQ&e=1540119442",
                "http://xwxt.hxll.wostore.cn/upload/woxueba/resource/images/feeb794b08e94a40bddc8e720dea8c49.jpg",
                "丁一晨的猫咪绘本（上）000001");
        videoList.add(video_6);
        videoList.add(video_7);
        videoList.add(video_8);
        videoList.add(video_9);
        videoList.add(video_10);
        //TODO 上面为测试代码，此处数据应从网络请求。
        adapter.refresh(videoList);
    }

    private void setUpRecyclerView() {
        adapter  = new VideoListAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
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

    /**
     * 测试类
     */
    public static class VideoObject{
        private String src;
        private String thumbnail;
        private String title;

        public VideoObject(String src, String thumbnail, String title) {
            this.src = src;
            this.thumbnail = thumbnail;
            this.title = title;
        }

        public String getSrc() {
            return src;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getTitle() {
            return title;
        }
    }
}
