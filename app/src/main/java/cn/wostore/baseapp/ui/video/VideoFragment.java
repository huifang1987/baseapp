package cn.wostore.baseapp.ui.video;

import butterknife.BindView;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.base.BaseFragment;
import cn.wostore.baseapp.widget.CustomToolBar;

/**
 * @author: Fanghui
 * @mail: fangh18@chinaunicom.cn
 * @date: 2018-4-12.
 */

public class VideoFragment extends BaseFragment {

	@BindView(R.id.tool_bar)
	CustomToolBar toolbar;


	@Override
	public int getLayoutId() {
		return R.layout.fragment_video;
	}

	@Override
	public void initPresenter() {

	}

	@Override
	public void initView() {
		setUpToolbar();
	}


	private void setUpToolbar() {
		toolbar = (CustomToolBar) rootView.findViewById(R.id.tool_bar);
		toolbar.setTitle(getString(R.string.video));
		toolbar.setShowBack(false);
	}
}
