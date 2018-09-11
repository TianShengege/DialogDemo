package com.example.user.test.Rt;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.user.test.Constant.Constant;
import com.example.user.test.VarietyData.IpAddress;
import com.example.user.test.Handler.XYHandler;
import com.example.user.test.Service.ApiService;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class WebJackListening {

    private  Context context;
    private  IpAddress ipAddress;
    public WebJackListening(Context context){
        this.context=context;
        this.ipAddress=new IpAddress(context);
    }


    public void getXycomIcon() {

        Retrofit tetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.URL_XYCOMLOGO).build();
        //拼接连接
        ApiService rqtServ = tetrofit.create(ApiService.class);
        Call<ResponseBody> call = rqtServ.getIcon();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("XYSDK",response.code()+"getXycomIcon====="+response.message()+"===="
                +response.headers()+"====="+response.raw()+"====="+response.body());
                if (response.code()==200) {
                    ipAddress.getIp();
                    sendMessage(response.code()+"getXycomIcon====="+response.message()+"===="
                            +response.headers()+"====="+response.raw()+"====="+response.body(),10);
                    Toast.makeText(context,"getXycomIcon正常加载",Toast.LENGTH_LONG).show();
                }else{
                    sendMessage(response.code()+"getXycomIcon====="+response.message()+"===="
                            +response.headers()+"====="+response.raw()+"====="+response.body(),11);
                    Toast.makeText(context,"getXycomIcon网页有可能被劫持，请知悉",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(context,"getXycomIcon网络断开或者请求链接错误，请求失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getXynetIcon() {
        Retrofit tetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.URL_XYNETLOGO).build();
        //拼接连接
        ApiService rqtServ = tetrofit.create(ApiService.class);
        Call<ResponseBody> call = rqtServ.getIcon();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("XYSDK",response.code()+"getXycomIcon====="+response.message()+"===="
                        +response.headers()+"====="+response.raw()+"====="+response.body());
                if (response.code()==200) {
                    ipAddress.getIp();
                    sendMessage(response.code()+"getXynetIcon====="+response.message()+"===="
                            +response.headers()+"====="+response.raw()+"====="+response.body(),11);
                    Toast.makeText(context,"getXynetIcon正常加载",Toast.LENGTH_LONG).show();
                }else{
                    sendMessage(response.code()+"getXynetIcon====="+response.message()+"===="
                            +response.headers()+"====="+response.raw()+"====="+response.body(),11);
                    Toast.makeText(context,"getXynetIcon网页有可能被劫持，请知悉",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"getXynetIcon网络断开或者请求链接错误，请求失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    private  void sendMessage(String str,int code){
        Message msg = new Message();
        msg.what = code;
        msg.obj = str;
        XYHandler.getInstance().sendMessage(msg);
    }

    }