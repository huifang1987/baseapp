package cn.wostore.baseapp.mvp.presenter;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import cn.wostore.baseapp.bean.Gank;
import cn.wostore.baseapp.mvp.contract.MainContract;


/**
 * Created by Nicholas on 2016/10/30.
 */

public class MainPresenter extends MainContract.Presenter {

    @Override
    public void getGank() {
        mModel.getGank().subscribe(new Observer<Gank>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManager.add(d);
                mView.showDialog();
            }

            @Override
            public void onNext(@NonNull Gank data) {
                mView.onSucceed(data);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.onFail(e.getMessage());
            }

            @Override
            public void onComplete() {
                mView.hideDialog();
            }
        });
    }
}
