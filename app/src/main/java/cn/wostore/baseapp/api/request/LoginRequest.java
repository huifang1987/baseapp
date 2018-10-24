package cn.wostore.baseapp.api.request;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Fanghui at 2018-10-23
 */
public class LoginRequest {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, RequestBody> getRequestBodyMap() {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody userNameRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this.userName);
        requestBodyMap.put("userName", userNameRequestBody);
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this.password);
        requestBodyMap.put("password", passwordRequestBody);
        return requestBodyMap;
    }
}
