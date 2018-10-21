package cn.wostore.baseapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.app.App;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.ui.map.MapActivity;
import cn.wostore.baseapp.utils.SharePreferencesUtil;
import cn.wostore.baseapp.utils.ToastUtil;

/**
 * Created by Fanghui at 2018-10-21
 */
public class LoginActivity extends BaseActivity{
    @BindView(R.id.edit_username)
    EditText userEt;
    @BindView(R.id.edit_password)
    EditText pwdEt;
    @BindView(R.id.btn_login)
    TextView loginBtn;


    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_login)
    public void login(){
        String username = userEt.getText().toString();
        String password = pwdEt.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            ToastUtil.showShort(this, App.getContext().getString(R.string.user_pwd_no_empety));
            return;
        }
        //TODO 登录
        SharePreferencesUtil.setLogin(true);
        SharePreferencesUtil.saveNickname(username);
        MapActivity.launch(this);
        finish();
    }

}
