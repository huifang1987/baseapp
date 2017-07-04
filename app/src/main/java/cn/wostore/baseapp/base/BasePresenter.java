package cn.wostore.baseapp.base;

import cn.wostore.baseapp.rx.RxManager;




public class BasePresenter<V extends BaseView, M extends BaseModel> {

    protected V mView;
    protected M mModel;
    protected RxManager mRxManager = new RxManager();

    public void setVM(V v, M m){
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart(){

    }

    public void onDestroy(){
        mRxManager.clear();
    }

}
