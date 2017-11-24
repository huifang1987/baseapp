package cn.wostore.baseapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import cn.wostore.baseapp.base.BaseFragment;
import java.util.List;

/**
 * @author: Fanghui
 * @mail: fangh18@chinaunicom.cn
 * @date: 2017-11-24.
 */

public class GankViewPagerAdapter extends FragmentStatePagerAdapter {
	private List<BaseFragment> fragmentList;
	private List<String> titleList;

	public GankViewPagerAdapter(FragmentManager fm,
		List<BaseFragment> fragmentList, List<String> titleList) {
		super(fm);
		this.fragmentList = fragmentList;
		this.titleList = titleList;
	}

	@Override
	public Fragment getItem(int position) {
		return fragmentList.get(position);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titleList.get(position);
	}
}
