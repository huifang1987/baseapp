package cn.wostore.baseapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.adapter.MainFragmentAdapter;
import cn.wostore.baseapp.base.BaseActivity;
import com.aspsine.fragmentnavigator.FragmentNavigator;

public class MainActivity extends BaseActivity {

	private static final int DEFAULT_POSITION = 0;

	private FragmentNavigator mNavigator;


	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
		= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_home:
					mNavigator.showFragment(0);
					return true;
				case R.id.navigation_dashboard:
					mNavigator.showFragment(1);
					return true;
				case R.id.navigation_notifications:
					mNavigator.showFragment(2);
					return true;
			}
			return false;
		}
	};

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	public void initPresenter() {

	}

	@Override
	public void initView() {
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNavigator = new FragmentNavigator(getSupportFragmentManager(), new MainFragmentAdapter(), R.id.container);
		mNavigator.setDefaultPosition(DEFAULT_POSITION);
		mNavigator.showFragment(mNavigator.getCurrentPosition());
		mNavigator.onCreate(savedInstanceState);
	}
}
