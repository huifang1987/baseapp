package cn.wostore.baseapp.adapter;

import static cn.wostore.baseapp.Constants.REQ_FAILED;
import static cn.wostore.baseapp.Constants.TYPE_ANDROID;
import static cn.wostore.baseapp.Constants.TYPE_IOS;
import static cn.wostore.baseapp.Constants.TYPE_OTHER;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wostore.baseapp.Constants;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.api.response.GetGankResponse.Result;
import cn.wostore.baseapp.widget.holder.LastHolder;
import cn.wostore.baseapp.widget.holder.MoreHolder;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;


public class ItemListAdapter extends RecyclerView.Adapter {

	private Context mContext;

	private static final int ITEM_TYPE_NORMOL = 0;
	private static final int ITEM_TYPE_MORE = 1;
	private static final int ITEM_TYPE_LAST = 2;


	/**
	 * 下一页页码，-1代表没有下一页
	 */
	private int nextPage = -1;
	/**
	 * 加载状态，1为加载中、2为加载失败
	 */
	private int requestStatus = 1;

	private List<Result> data = new ArrayList<>();

	private OnItemClickListener onItemClickListener;

	private OnRetryClickListener onRetryClickListener;

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view;
		mContext = parent.getContext();
		switch (viewType) {
			case ITEM_TYPE_NORMOL:
				view = LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false);
				return new DataViewHolder(view);
			case ITEM_TYPE_MORE:
				view = LayoutInflater.from(mContext).inflate(R.layout.layout_more, parent, false);
				return new MoreHolder(view);
			case ITEM_TYPE_LAST:
				view = LayoutInflater.from(mContext).inflate(R.layout.layout_last, parent, false);
				return new LastHolder(view);
		}
		return null;
	}

	@Override
	public void onBindViewHolder(ViewHolder _holder, int position) {
		switch (getItemViewType(position)){
			case ITEM_TYPE_NORMOL:
				DataViewHolder viewHolder = (DataViewHolder) _holder;
				Result bean = data.get(position);
				if (TYPE_ANDROID.equals(bean.getType())){
					Glide.with(mContext)
						.load(R.mipmap.ic_android)
						.into(viewHolder.icon);
				} else if (TYPE_IOS.equals(bean.getType())){
					Glide.with(mContext)
						.load(R.mipmap.ic_ios)
						.into(viewHolder.icon);
				} else if (TYPE_OTHER.equals(bean.getType())){
					Glide.with(mContext)
						.load(R.mipmap.ic_news)
						.into(viewHolder.icon);
				}
				viewHolder.desc.setText(bean.getDesc());
				viewHolder.time.setText(bean.getPublishedAt());
				viewHolder.frame.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						onItemClickListener.onClick();
					}
				});
				break;
			case ITEM_TYPE_MORE:
				MoreHolder holder = (MoreHolder) _holder;
				if (requestStatus == Constants.REQUESTING) {
					holder.loadingIcon.setVisibility(View.VISIBLE);
					holder.loadMoreTv.setText(R.string.loading);
				} else if (requestStatus == Constants.REQ_FAILED) {
					holder.loadingIcon.setVisibility(View.GONE);
					holder.loadMoreTv.setText(R.string.load_failed);
					holder.loadMoreTv.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (requestStatus == Constants.REQ_FAILED){
								onRetryClickListener.onClick();
							}
						}
					});
				}
				break;
			case ITEM_TYPE_LAST:
				break;
		}
	}

	@Override
	public int getItemCount() {
		if (data.size() == 0){
			return 0;
		}else {
			return data.size()+1;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (nextPage != -1 && position == getItemCount() - 1) {
			return ITEM_TYPE_MORE;
		}

		if (nextPage == -1 && position == getItemCount() - 1) {
			return ITEM_TYPE_LAST;
		}

		return ITEM_TYPE_NORMOL;
	}

	/**
	 * 初次加载或刷新
	 */
	public void refresh(List<Result> data) {
		this.data.clear();
		this.data.addAll(data);
		notifyDataSetChanged();
	}

	/**
	 * 加载更多
	 */
	public void loadMore(List<Result> data) {
		this.data.addAll(data);
		notifyDataSetChanged();
	}

	public void setRequestStatus(int requestStatus) {
		this.requestStatus = requestStatus;
		if (requestStatus == REQ_FAILED){
			notifyDataSetChanged();
		}
	}

	public int getRequestStatus() {
		return requestStatus;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setOnItemClickListener(
		OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void setOnRetryClickListener(
		OnRetryClickListener onRetryClickListener) {
		this.onRetryClickListener = onRetryClickListener;
	}

	public static class DataViewHolder extends RecyclerView.ViewHolder{

		@BindView(R.id.iv_icon)
		ImageView icon;
		@BindView(R.id.tv_des)
		TextView desc;
		@BindView(R.id.tv_time)
		TextView time;
		@BindView(R.id.rl_frame)
		RelativeLayout frame;

		public DataViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

	public interface OnRetryClickListener{
		void onClick();
	}

	public interface OnItemClickListener{
		void onClick();
	}
}
