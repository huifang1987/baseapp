package cn.wostore.baseapp.api;

import io.reactivex.Observable;
import cn.wostore.baseapp.api.response.GetGankResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiService {

    String BASE_URL="http://gank.io/";

    @GET("api/data/{type}/10/{page}")
    Observable<GetGankResponse> getGank(@Path("type") String type, @Path("page") int page);

}
