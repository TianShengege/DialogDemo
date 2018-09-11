package com.example.user.test.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * url添加后缀,Retrofit框架使用
 */
public interface ApiService {
    @GET("basil2style")
    Call<ResponseBody> getString();

    @GET("favicon.ico")
    Call<ResponseBody> getIcon();

    @GET("cdn.txt")
    Call<ResponseBody> getCdnDomain();

    @GET("32.jpg")
    Call<ResponseBody> getImage();
    @GET
    Call<ResponseBody> sendURL(
            @Url String urls,
            @Query("cdn") String cdn,
            @Query("url") String url,
            @Query("bodysize") long bodysize,
            @Query("response_time") long response_time,
            @Query("status_code") int status_code,
            @Query("client_ip") String client_ip
    );

}
