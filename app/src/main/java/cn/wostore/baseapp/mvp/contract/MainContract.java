package cn.wostore.baseapp.mvp.contract;

import io.reactivex.Observable;
import cn.wostore.baseapp.base.BaseModel;
import cn.wostore.baseapp.base.BasePresenter;
import cn.wostore.baseapp.base.BaseView;
import cn.wostore.baseapp.bean.Gank;



public interface MainContract {

    interface View extends BaseView {

        void showDialog();

        void onSucceed(Gank data);

        void onFail(String err);

        void hideDialog();

    }

    interface Model extends BaseModel {
        Observable<Gank> getGank();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getGank();
    }
}
