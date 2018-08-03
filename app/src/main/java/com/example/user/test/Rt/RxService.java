package com.example.user.test.Rt;

import retrofit2.http.GET;
import rx.Observable;

public interface RxService {
    @GET("basil2style")
    Observable<RxText> getString();
}
