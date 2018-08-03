package com.example.user.test.Rt;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

public class Retrofits {
    static Retrofits retrofit;
    public static Retrofits getInstance(){
        if(retrofit==null){
            retrofit=new Retrofits();
        }
        return  retrofit;
    }
    public void initRetrofit(final TextView tv) {
        //获取Retrofit对象，设置地址,使用makeRetifit()需要加addCallAdapter
        Retrofit tetrofit=new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL_BASE).build();

//        makeRetifit( tv,tetrofit);
        makeRx(tv,tetrofit);
    }

    private void makeRx(final TextView tv, Retrofit tetrofit) {
        //拼接连接
        RxService rqtServ=tetrofit.create(RxService.class);
        Rxloader.asyncNetworkSubscribe(rqtServ.getString(), new Subscriber<RxText>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(RxText rxText) {
                int st=rxText.id;
                String sts=rxText.login;
                tv.setText(st+sts);
            }
        });

    }

    private void makeRetifit(final TextView tv, Retrofit tetrofit) {
        //拼接连接
        RequestServices rqtServ=tetrofit.create(RequestServices.class);
        Call<ResponseBody> call=rqtServ.getString();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    //返回的结果保存在response.body()中
                    try {
                        String result = response.body().string();
                        Gson gson=new Gson();
                        Student student = gson.fromJson(result, Student.class);
                        Log.i("XYSDK",student.toString());
                        tv.setText(student.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //onResponse方法是运行在主线程也就是UI线程的，所以我们可以在这里
                    //直接更新UI
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
