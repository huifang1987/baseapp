package cn.wostore.baseapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.ui.video.VideoListActivity;
import cn.wostore.baseapp.ui.video.VideoListActivity.VideoObject;

/**
 * Created by Fanghui at 2018-10-21
 */
public class VideoListAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<VideoObject> videoList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder _holder, int position) {
        VideoViewHolder holder = (VideoViewHolder) _holder;
        VideoObject videoInfo = videoList.get(position);
        holder.titleTv.setText(videoInfo.getTitle());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public void refresh(List<VideoObject> list){
        videoList.clear();
        videoList.addAll(list);
        notifyDataSetChanged();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title)
        TextView titleTv;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}