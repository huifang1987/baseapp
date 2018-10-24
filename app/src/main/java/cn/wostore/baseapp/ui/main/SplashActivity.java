package cn.wostore.baseapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.manager.AppManager;
import cn.wostore.baseapp.ui.login.LoginActivity;
import cn.wostore.baseapp.ui.map.MapActivity;
import cn.wostore.baseapp.utils.SharePreferencesUtil;


public class SplashActivity extends BaseActivity {

    private static final long DELAY_TIME = 1000;
    private int times = 3; //3秒后自动跳过

    @BindView(R.id.skip)
    LinearLayout jump;

    @BindView(R.id.btn_enter)
    TextView enterBtn;

    @BindView(R.id.count)
    TextView count;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            times--;
            if (count != null) {
                count.setText(times + "s");
            }
            if (times > 0) {
                Message message = handler.obtainMessage(0);
                handler.sendMessageDelayed(message, DELAY_TIME);
            } else {
                navigate();
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        count.setText(times + "s");
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (handler != null) {
                    handler.removeMessages(0);
                }
                navigate();
            }
        });
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (handler != null) {
                    handler.removeMessages(0);
                }
                navigate();
            }
        });
        Message message = handler.obtainMessage(0);
        handler.sendMessageDelayed(message, DELAY_TIME);
    }


    private void navigate() {
        if (SharePreferencesUtil.isLogged()){
            MapActivity.launch(this);
        } else {
            LoginActivity.launch(this);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppManager.getAppManager().AppExit(SplashActivity.this, false);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeMessages(0);
        }
        super.onDestroy();
    }

}
