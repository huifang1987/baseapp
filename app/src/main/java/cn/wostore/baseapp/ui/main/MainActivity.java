package cn.wostore.baseapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.adapter.MainFragmentAdapter;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.manager.AppManager;
import com.aspsine.fragmentnavigator.FragmentNavigator;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {


	@BindView(R.id.bottom_layout)
	LinearLayoutCompat bottomLayout;

	private static final Integer[] ID_ARRAY = {
		R.id.main_bottom_nv_game, R.id.main_bottom_nv_vip, R.id.main_bottom_nv_store, R.id.main_bottom_nv_me
	};

	private static final List<Integer> IDS = Arrays.asList(ID_ARRAY);

	private static final int DEFAULT_POSITION = 0;

	private FragmentNavigator mFragmentNavigator;

	private int selectViewId;

	/**
	 * 用于两次点击退出
	 */
	private boolean isExit;

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void initPresenter() {

	}

	@Override
	public void initView(Bundle savedInstanceState) {
		mFragmentNavigator =
			new FragmentNavigator(getSupportFragmentManager(), new MainFragmentAdapter(), R.id.container);
		mFragmentNavigator.setDefaultPosition(DEFAULT_POSITION);
		selectViewId = IDS.get(DEFAULT_POSITION);
		mFragmentNavigator.onCreate(savedInstanceState);
		for (int i = 0; i < bottomLayout.getChildCount(); i++) {
			bottomLayout.getChildAt(i).setOnClickListener(this);
		}
		showTabUI(findViewById(selectViewId));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mFragmentNavigator.onSaveInstanceState(outState);
	}

	/**
	 * 底部切换UI
	 */
	private void showTabUI(View v) {
		mFragmentNavigator.showFragment(IDS.indexOf(v.getId()), false, true);
		for (int i : IDS) {
			RelativeLayout relativeLayout = (RelativeLayout) findViewById(i);
			relativeLayout.getChildAt(0).setSelected(v.getId() == i);
		}
		selectViewId = v.getId();
	}

	@Override
	public void onClick(View v) {
		if (selectViewId == v.getId()) {
			return;
		}
		switch (IDS.indexOf(v.getId())) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
		}
		showTabUI(v);
	}

	/**
	 * 双击back键退出App
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (IDS.get(DEFAULT_POSITION) != selectViewId) {
				showTabUI(findViewById(IDS.get(DEFAULT_POSITION)));
			} else {
				if (!isExit) {
					Toast.makeText(this, getString(R.string.app_quit_toast), Toast.LENGTH_SHORT)
						.show();
					isExit = true;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							isExit = false;
						}
					}, 2000);
				} else {
					AppManager.getAppManager().AppExit(MainActivity.this, false);

				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
