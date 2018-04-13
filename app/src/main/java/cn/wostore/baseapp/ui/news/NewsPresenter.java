package cn.wostore.baseapp.ui.news;

import android.util.Log;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import cn.wostore.baseapp.api.response.GetGankResponse;


/**
 * Created by Nicholas on 2016/10/30.
 */

public class NewsPresenter extends NewsContract.Presenter {

    @Override
    public void getGank(int pageNum) {
        mModel.getGank(pageNum).subscribe(new Observer<GetGankResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManager.add(d);
            }

            @Override
            public void onNext(@NonNull GetGankResponse data) {
                Log.d("fanghui", data.toString());
                mView.onSucceed(data);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.onFail("error");
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
