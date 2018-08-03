package com.example.user.test.Rt;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestServices {
    @GET("basil2style")
    Call<ResponseBody> getString();
}
