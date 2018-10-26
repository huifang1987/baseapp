package cn.wostore.baseapp.api;

import java.util.Map;

import cn.wostore.baseapp.api.response.GetTerminalListResponse;
import cn.wostore.baseapp.api.response.GetVideoListResponse;
import cn.wostore.baseapp.api.response.LoginResponse;
import io.reactivex.Observable;
import cn.wostore.baseapp.api.response.GetGankResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;


public interface ApiService {

    String BASE_URL="http://47.96.124.141:8080/cloud-command/";

    @Multipart
    @POST("user/loginSubmit")
    Observable<LoginResponse> login(@PartMap Map<String, RequestBody> request);

    @Multipart
    @POST("terminal/getList")
    Observable<GetTerminalListResponse> getTerminalList(@PartMap Map<String, RequestBody> request);

    @Multipart
    @POST("video/getVideoList")
    Observable<GetVideoListResponse> getVideoList(@PartMap Map<String, RequestBody> request);


}
