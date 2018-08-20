package com.example.user.test.Rt;

import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.user.test.Constant.Constant;
import com.example.user.test.Entity.Student;
import com.example.user.test.Handler.XYHandler;
import com.example.user.test.Service.RequestServices;
import com.example.user.test.Service.RxService;
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

    public void initRetrofit() {
        //获取Retrofit对象，设置地址,使用makeRetifit()需要加addCallAdapter
        Retrofit tetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL_BASE).build();

//        makeRetrofit(tetrofit);
        makeRx(tetrofit);
    }

    private void makeRx( Retrofit tetrofit) {
        //拼接连接
        RxService rqtServ = tetrofit.create(RxService.class);
        Rxloader.asyncNetworkSubscribe(rqtServ.getString(), new Subscriber<RxText>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(RxText rxText) {
                int st = rxText.id;
                String sts = rxText.login;
                sendMessage(sts);
            }
        });

    }

    private void makeRetrofit( Retrofit tetrofit) {
        //拼接连接
        RequestServices rqtServ = tetrofit.create(RequestServices.class);
        Call<ResponseBody> call = rqtServ.getString();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //返回的结果保存在response.body()中
                    try {
                        String result = response.body().string();
                        Gson gson = new Gson();
                        Student student = gson.fromJson(result, Student.class);
                        Log.i("XYSDK", student.toString());
                        sendMessage(student.login);
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

  private  void sendMessage(String str){
      Message msg = new Message();
      msg.what = Constant.REQUEST_SUCCESS;
      msg.obj = str;
      XYHandler.getInstance().sendMessage(msg);
  }

}
