package cn.wostore.baseapp.adapter;

import android.support.v4.app.Fragment;
import cn.wostore.baseapp.ui.fragment.GankFragment;
import cn.wostore.baseapp.ui.fragment.HomeFragment;
import cn.wostore.baseapp.ui.fragment.MeFragment;
import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;


/**
 * Created by aspsine on 16/4/28.
 */
public class MainFragmentAdapter implements FragmentNavigatorAdapter {

    @Override
    public Fragment onCreateFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new GankFragment();
            case 2:
                return new MeFragment();
        }
        return null;
    }

    @Override
    public String getTag(int position) {
        return String.valueOf(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}