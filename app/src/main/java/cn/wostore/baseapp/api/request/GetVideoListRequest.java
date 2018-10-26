package cn.wostore.baseapp.api.request;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Fanghui at 2018-10-24
 */
public class GetVideoListRequest {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, RequestBody> getRequestBodyMap() {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody userIdRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this.userId);
        requestBodyMap.put("userId", userIdRequestBody);
        return requestBodyMap;
    }
}
