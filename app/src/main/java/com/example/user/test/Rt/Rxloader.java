package com.example.user.test.Rt;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Rxloader {
    public static <T> Observable<T> asyncTask(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public static <T> Observable<T> asyncNetwork(Observable<T> observable) {
        return asyncTask(observable)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Subscription asyncNetworkSubscribe(Observable<T> observable,
                                                         Subscriber<T> subscriber) {
        Observable<T> asyncObservable = asyncNetwork(observable);
        return subscriber == null ?
                asyncObservable.subscribe() : asyncObservable.subscribe(subscriber);
    }

    public static <T> Subscription asyncNetworkSubscribe(Observable<T> observable) {
        return asyncNetworkSubscribe(observable, null);
    }
}
