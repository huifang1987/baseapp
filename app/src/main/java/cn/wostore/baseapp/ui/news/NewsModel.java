package cn.wostore.baseapp.ui.news;

import static cn.wostore.baseapp.Constants.TYPE_OTHER;

import io.reactivex.Observable;
import cn.wostore.baseapp.api.ApiEngine;
import cn.wostore.baseapp.api.response.GetGankResponse;
import cn.wostore.baseapp.rx.RxSchedulers;

/**
 * Created by Nicholas on 2016/10/30.
 */

public class NewsModel implements NewsContract.Model {

    @Override
    public Observable<GetGankResponse> getGank(int pageNum) {
        return ApiEngine.getInstance().getService()
                .getGank(TYPE_OTHER, pageNum)
                .compose(RxSchedulers.<GetGankResponse>io_main());
    }
}
