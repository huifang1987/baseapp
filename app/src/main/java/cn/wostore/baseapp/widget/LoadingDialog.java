package cn.wostore.baseapp.widget;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.utils.L;

/**
 * @author: Fanghui
 * @date: 2018-4-13.
 */

public class LoadingDialog extends DialogFragment {

	private static final String TAG = LoadingDialog.class.getSimpleName();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//改变Dialog样式
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		//点击外部不消失
		getDialog().setCanceledOnTouchOutside(false);
		// 点击Back键不消失
		getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}
		});
		View v = inflater.inflate(R.layout.fragment_loading, container, false);

		return v;
	}


	public static LoadingDialog newInstance() {
		LoadingDialog fragment = new LoadingDialog();
		return fragment;
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		try {
			Fragment fragment = manager.findFragmentByTag(tag);
			if(fragment ==null || !fragment.isAdded()){
				super.show(manager,tag);
			} else {
				L.d(TAG, fragment.toString()+" is Added before!");
			}
		} catch (Exception e) {
			L.e(TAG, e.toString());
		}
	}
}
