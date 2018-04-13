package cn.wostore.baseapp.api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.wostore.baseapp.app.App;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiEngine {

    private volatile static ApiEngine apiEngine;
    private Retrofit retrofit;

    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 15 * 1000;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 15 * 1000;

    private ApiEngine() {

        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(new NetWorkInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }

    /**
     * Gets api service.
     *
     * @return the api service
     */
    public ApiService getService() {
        return retrofit.create(ApiService.class);
    }
}
