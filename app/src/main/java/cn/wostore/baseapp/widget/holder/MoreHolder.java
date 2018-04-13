package cn.wostore.baseapp.widget.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wostore.baseapp.R;


public class MoreHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_load_more)
    public TextView loadMoreTv;
    @BindView(R.id.app_progress)
    public ProgressBar loadingIcon;

    public MoreHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
