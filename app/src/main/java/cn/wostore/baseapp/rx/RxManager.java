package cn.wostore.baseapp.rx;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by fanghui on 2017-7-3.
 */

public class RxManager {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 单纯的Observables 和 Subscribers管理
     * @param m
     */
    public void add(Disposable m) {
        /*订阅管理*/
        mCompositeDisposable.add(m);
    }

    /**
     * 单个presenter生命周期结束
     */
    public void clear() {
        mCompositeDisposable.clear();// 取消所有订阅

    }

}
