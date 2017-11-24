package cn.wostore.baseapp.ui.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.adapter.GankViewPagerAdapter;
import cn.wostore.baseapp.base.BaseFragment;
import cn.wostore.baseapp.ui.activity.MainActivity;
import cn.wostore.baseapp.utils.CommonUtil;
import cn.wostore.baseapp.widget.CustomToolBar;
import java.util.ArrayList;
import java.util.List;

public class GankFragment extends BaseFragment {
	private static final String TAG = GankFragment.class.getCanonicalName();

	private CustomToolBar mCustomToolBar;
	private Toolbar toolbar;
	private AppBarLayout appBar;
	private TabLayout tabs;
	private ViewPager viewPager;

	private MainActivity activity;
	private CollapsingToolbarLayoutState state;

	private GankViewPagerAdapter mGankViewPagerAdapter;

	@Override
	public int getLayoutId() {
		return R.layout.fragment_gank;
	}

	@Override
	public void initPresenter() {

	}

	@Override
	public void initView() {
		activity = (MainActivity) getActivity();
		toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
		activity.setSupportActionBar(toolbar);
		appBar = (AppBarLayout) rootView.findViewById(R.id.app_bar);
		mCustomToolBar = (CustomToolBar) rootView.findViewById(R.id.tool_bar);
		viewPager = (ViewPager) rootView.findViewById(R.id.vp);
		tabs = (TabLayout) rootView.findViewById(R.id.tabs);
		setupToolBar();
		setupAppBar();
		setupViewPager();
	}

	private void setupToolBar() {
		mCustomToolBar.setBackActionItemResId(R.mipmap.icon_search);
		mCustomToolBar.setShowRightActionItem(true);
		mCustomToolBar.setRightActionItemResId(R.mipmap.icon_category);
		mCustomToolBar.setTitle(getString(R.string.title_dashboard));
	}

	private void setupAppBar() {
		appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				if (verticalOffset == 0){
					if (state != CollapsingToolbarLayoutState.EXPANDED){
						state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
					}
					//mCustomToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));
					mCustomToolBar.setShowTitle(false);
					Log.d(TAG, "展开");
				} else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
					if (state != CollapsingToolbarLayoutState.COLLAPSED){
						state = CollapsingToolbarLayoutState.COLLAPSED; //修改状态标记为折叠
					}
					//mCustomToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
					mCustomToolBar.setShowTitle(true);
					Log.d(TAG, "折叠");
				}else {
					if (state != CollapsingToolbarLayoutState.INTERNEDIATE){
						state = CollapsingToolbarLayoutState.INTERNEDIATE;
					}
				}

				int dy = Math.abs(verticalOffset);
				float alpha = (float) dy / appBarLayout.getTotalScrollRange();
				mCustomToolBar.setBackgroundColor(CommonUtil.getColorWithAlpha(getResources().getColor(R.color.colorPrimary), alpha));
			}
		});
	}

	private void setupViewPager(){

		List<BaseFragment> fragmentList = new ArrayList<>();
		fragmentList.add(new TabAFragment());
		fragmentList.add(new TabBFragment());

		List<String> titleList = new ArrayList<>();
		titleList.add(getString(R.string.tab_a));
		titleList.add(getString(R.string.tab_b));

		mGankViewPagerAdapter = new GankViewPagerAdapter(getChildFragmentManager(), fragmentList, titleList);
		viewPager.setAdapter(mGankViewPagerAdapter);
		tabs.setupWithViewPager(viewPager);

	}

	private enum CollapsingToolbarLayoutState {
		EXPANDED,   //展开
		COLLAPSED,  //折叠
		INTERNEDIATE    //中间状态
	}


}
