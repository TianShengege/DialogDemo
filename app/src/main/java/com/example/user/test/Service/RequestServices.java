package com.example.user.test.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * url添加后缀,Retrofit框架使用
 */
public interface RequestServices {
    @GET("basil2style")
    Call<ResponseBody> getString();
}
