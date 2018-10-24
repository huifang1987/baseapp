package cn.wostore.baseapp.api.request;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Fanghui at 2018-10-24
 */
public class GetTerminalListRequest {
    private String userId;
    private String status;
    private String condition;
    private String page;
    private String pageSize;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, RequestBody> getRequestBodyMap() {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody userIdRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this.userId);
        requestBodyMap.put("userId", userIdRequestBody);
//        RequestBody statusRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this.status);
//        requestBodyMap.put("status", statusRequestBody);
//        RequestBody conditionRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this.condition);
//        requestBodyMap.put("condition", conditionRequestBody);
//        RequestBody pageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this.page);
//        requestBodyMap.put("page", pageRequestBody);
//        RequestBody pageSizeRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this.pageSize);
//        requestBodyMap.put("pageSize", pageSizeRequestBody);
        return requestBodyMap;
    }
}
