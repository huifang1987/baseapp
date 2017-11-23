package cn.wostore.baseapp.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.base.BaseFragment;
import cn.wostore.baseapp.ui.activity.MainActivity;

public class MeFragment extends BaseFragment {

	MainActivity activity;

	@Override
	public int getLayoutId() {
		return R.layout.fragment_me;
	}

	@Override
	public void initPresenter() {

	}

	@Override
	public void initView() {
		activity = (MainActivity) getActivity();
		Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
		activity.setSupportActionBar(toolbar);
		FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
			}
		});
	}
}
