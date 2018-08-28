package com.example.user.test.Service;


import retrofit2.http.GET;
import rx.Observable;


/**
 * url添加后缀，Rx框架使用
 */
public interface RxService {
    @GET("basil2style")
    Observable<RxText> getString();
}
