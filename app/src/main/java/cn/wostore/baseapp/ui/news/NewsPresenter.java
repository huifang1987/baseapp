package cn.wostore.baseapp.ui.news;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import cn.wostore.baseapp.api.response.GetGankResponse;


/**
 * Created by Nicholas on 2016/10/30.
 */

public class NewsPresenter extends NewsContract.Presenter {

    @Override
    public void getGank() {
        mModel.getGank().subscribe(new Observer<GetGankResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManager.add(d);
                mView.showDialog();
            }

            @Override
            public void onNext(@NonNull GetGankResponse data) {
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
