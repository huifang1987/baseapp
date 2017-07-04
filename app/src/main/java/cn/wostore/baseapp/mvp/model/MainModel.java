package cn.wostore.baseapp.mvp.model;

import io.reactivex.Observable;
import cn.wostore.baseapp.api.ApiEngine;
import cn.wostore.baseapp.bean.Gank;
import cn.wostore.baseapp.mvp.contract.MainContract;
import cn.wostore.baseapp.rx.RxSchedulers;

/**
 * Created by Nicholas on 2016/10/30.
 */

public class MainModel implements MainContract.Model {

    @Override
    public Observable<Gank> getGank() {
        return ApiEngine.getInstance().getApiService()
                .getGank("1")
                .compose(RxSchedulers.<Gank>io_main());
    }
}
