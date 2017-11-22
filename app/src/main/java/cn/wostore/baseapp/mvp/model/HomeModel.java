package cn.wostore.baseapp.mvp.model;

import io.reactivex.Observable;
import cn.wostore.baseapp.api.ApiEngine;
import cn.wostore.baseapp.api.response.GetGankResponse;
import cn.wostore.baseapp.mvp.contract.HomeContract;
import cn.wostore.baseapp.rx.RxSchedulers;

/**
 * Created by Nicholas on 2016/10/30.
 */

public class HomeModel implements HomeContract.Model {

    @Override
    public Observable<GetGankResponse> getGank() {
        return ApiEngine.getInstance().getApiService()
                .getGank("1")
                .compose(RxSchedulers.<GetGankResponse>io_main());
    }
}
