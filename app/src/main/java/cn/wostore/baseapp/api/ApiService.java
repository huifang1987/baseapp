package cn.wostore.baseapp.api;

import io.reactivex.Observable;
import cn.wostore.baseapp.bean.Gank;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiService {

    String BASE_URL="http://gank.io/";

    @GET("api/data/Android/10/{page}")
    Observable<Gank> getGank(@Path("page") String page);

}
