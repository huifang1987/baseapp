package cn.wostore.baseapp.app;

import android.app.Application;

import com.facebook.stetho.Stetho;


public class App extends Application {

    private static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //调试工具Stetho
        Stetho.initializeWithDefaults(this);
    }

    public static App getContext() {
        return mContext;
    }
}
