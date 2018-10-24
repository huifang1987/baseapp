package cn.wostore.baseapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.wostore.baseapp.Constants;
import cn.wostore.baseapp.R;
import cn.wostore.baseapp.api.ApiEngine;
import cn.wostore.baseapp.api.request.LoginRequest;
import cn.wostore.baseapp.api.response.LoginResponse;
import cn.wostore.baseapp.app.App;
import cn.wostore.baseapp.base.BaseActivity;
import cn.wostore.baseapp.rx.RxSchedulers;
import cn.wostore.baseapp.ui.map.MapActivity;
import cn.wostore.baseapp.utils.L;
import cn.wostore.baseapp.utils.MD5Util;
import cn.wostore.baseapp.utils.SharePreferencesUtil;
import cn.wostore.baseapp.utils.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Fanghui at 2018-10-21
 */
public class LoginActivity extends BaseActivity{

    private static final String TAG = LoginActivity.class.getSimpleName();

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
            ToastUtil.showShort(this, mContext.getResources().getString(R.string.user_pwd_no_empety));
            return;
        }
        LoginRequest request = new LoginRequest();
        request.setUserName(username);
        request.setPassword(MD5Util.toMD5(password));
        loginBtn.setEnabled(false);
        loginBtn.setText(mContext.getResources().getString(R.string.login_ing));
        ApiEngine.getInstance().getService().login(request.getRequestBodyMap())
                .compose(RxSchedulers.<LoginResponse>io_main())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        try {
                            if (Constants.SUCCESS_RESP.equals(loginResponse.getSuccess())){
                                SharePreferencesUtil.setLogin(true);
                                SharePreferencesUtil.saveUsername(loginResponse.getData().getUserName());
                                SharePreferencesUtil.saveUserID(loginResponse.getData().getId());
                                MapActivity.launch(mContext);
                            } else {
                                ToastUtil.showShort(mContext, loginResponse.getMessage());
                                loginBtn.setEnabled(true);
                                loginBtn.setText(mContext.getResources().getString(R.string.login));
                            }
                        } catch (Exception e) {
                            ToastUtil.showShort(mContext, mContext.getResources().getString(R.string.login_fail));
                            loginBtn.setEnabled(true);
                            loginBtn.setText(mContext.getResources().getString(R.string.login));

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(mContext, mContext.getResources().getString(R.string.login_fail));
                        loginBtn.setEnabled(true);
                        loginBtn.setText(mContext.getResources().getString(R.string.login));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
