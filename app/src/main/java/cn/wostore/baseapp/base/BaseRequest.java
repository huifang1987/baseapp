package cn.wostore.baseapp.base;

import java.util.Random;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Alex_shen on 2017/5/16.
 */

public abstract class BaseRequest {
    public abstract String toJSONObjectString();

    public RequestBody getRequestBody(){
        RequestBody body = RequestBody
			.create(MediaType.parse("application/json; charset=utf-8"), toJSONObjectString());
        return body;
    }

}
