package cn.wostore.baseapp.ui.news;

import io.reactivex.Observable;
import cn.wostore.baseapp.base.BaseModel;
import cn.wostore.baseapp.base.BasePresenter;
import cn.wostore.baseapp.base.BaseView;
import cn.wostore.baseapp.api.response.GetGankResponse;



public interface NewsContract {

    interface View extends BaseView {

        void onSucceed(GetGankResponse data);

        void onFail(String err);

    }

    interface Model extends BaseModel {
        Observable<GetGankResponse> getGank(int pageNum);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getGank(int pageNum);
    }
}
