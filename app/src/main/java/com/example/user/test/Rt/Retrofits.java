package com.example.user.test.Rt;

import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    static  Retrofit retrofit;
    public static Retrofit getInstance(){
        if(retrofit==null){
            retrofit=new Retrofit();
        }
        return  retrofit;
    }
    public void initRetrofit(final TextView tv) {
        //获取Retrofit对象，设置地址
        Retrofit tetrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL_BASE).build();
        RequestServices rqtServ=tetrofit.create(RequestServices.class);
        Call<ResponseBody> call=rqtServ.getString();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccess()){
                    //返回的结果保存在response.body()中
                    try {
                        String result = response.body().string();
//                        Log.i("XYSDK",result.toString());
//                        Gson gson=new Gson();
//                        Student student = gson.fromJson(result, Student.class);
//                        Log.i("XYSDK",student.toString());
                        tv.setText(result);
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
