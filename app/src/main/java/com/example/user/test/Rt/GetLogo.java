package com.example.user.test.Rt;

import android.content.Context;
import android.widget.Toast;

import com.example.user.test.Constant.Constant;
import com.example.user.test.CreateImea.GetIp;
import com.example.user.test.Service.RequestServices;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class GetLogo {

    private  Context context;

    public GetLogo(Context context){
        this.context=context;
    }

    public void initRetrofit() {
        //获取Retrofit对象，设置地址,使用makeRetifit()需要加addCallAdapter
        Retrofit tetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.URL_LOGO).build();

        makeRetrofit(tetrofit);
    }

    private void makeRetrofit(Retrofit tetrofit) {
        //拼接连接
        RequestServices rqtServ = tetrofit.create(RequestServices.class);
        Call<ResponseBody> call = rqtServ.getLogo();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.message().equals("OK")) {
                    new GetIp(context).getIp();
                    Toast.makeText(context,"正常加载"+response.message(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"网页有可能被劫持，请知悉",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"网络断开，请求失败",Toast.LENGTH_LONG).show();
            }
        });
    }
}