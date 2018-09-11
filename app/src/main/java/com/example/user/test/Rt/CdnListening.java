package com.example.user.test.Rt;

import android.util.Log;

import com.example.user.test.Constant.Constant;
import com.example.user.test.Entity.CdnEntity;
import com.example.user.test.Service.ApiService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by TianShen on 2018/9/10.
 */
public class CdnListening {


    private static CdnListening cdnListening;

    private CdnListening() {
    } // 单例

    public static CdnListening getInstance() {
        if (null == cdnListening) {
            cdnListening = new CdnListening();
        }
        return cdnListening;
    }

    public void handlerRequest() {
        getCdnText();
    }

     String[] cdnDomain;

    /**
     * 获取cdn文本
     */
    private void getCdnText() {
        Retrofit tetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.CDN_URL_DOMAIN).build();
        //拼接连接
        ApiService rqtServ = tetrofit.create(ApiService.class);
        Call<ResponseBody> call = rqtServ.getCdnDomain();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    //返回的结果保存在response.body()中
                    String resultDomain = null;
                    String resultURL = null;
                    try {
                        String body = response.body().string();
                        resultURL = body.replaceAll("\n", ",");
                        resultDomain = resultURL.replaceAll("/32.jpg", "");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String str = ",";
                   cdnDomain = resultDomain.split(str);
                    initData();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 遍历cdn数组
     */
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < cdnDomain.length; i++) {
                    CdnEntity cdnEntity = new CdnEntity();
                    try {
                        InetAddress x = InetAddress.getByName(cdnDomain[i]);
                        cdnEntity.setClient_ip(splitData(x.getLocalHost().toString(), "/"));
                        cdnEntity.setCdn(splitData(x.toString(), "/"));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
//                    Log.i("XYSDK", i+"initData------------------------------" + cdnEntity.CDN_URL());
                    getImage(cdnEntity,cdnDomain[i]);

                }
            }
        }).start();
    }

    /**
     * 请求图片
     *
     * @param cdnEntity
     * @param domain
     */
    private void getImage(final CdnEntity cdnEntity, final String domain) {
        String urls="http://" + domain + "/";
        Retrofit tetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(urls).build();
        //记录请求耗时
        final long startNs = System.nanoTime();
        //拼接连接
        ApiService rqtServ = tetrofit.create(ApiService.class);
        final Call<ResponseBody> call = rqtServ.getImage();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                cdnEntity.setResponse_time((int)(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)));
                cdnEntity.setBodysize((int)(response.body().contentLength()));
                cdnEntity.setStatus_code(response.code());
                cdnEntity.setUrl(domain + "/32.jpg");
                Log.i("XYSDK", "getImage------------------------------" + cdnEntity.CDN_URL());
                sendCDN(cdnEntity);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("XYSDK","getImage 失败");
            }
        });
    }

    /**
     * 发送cdn请求
     * @param cdnEntity
     */
    private void sendCDN(final CdnEntity cdnEntity) {

        Retrofit tetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.CDN_URL_REQUEST).build();
        Call<ResponseBody> call = tetrofit.create(ApiService.class).sendURL("", cdnEntity.getCdn(), cdnEntity.getUrl(),
                cdnEntity.getBodysize(), cdnEntity.getResponse_time(), cdnEntity.getStatus_code(), cdnEntity.getClient_ip());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("XYSDK", "sendCDN = "+cdnEntity.getUrl()+"");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("XYSDK", "sendCDN  报错");
            }
        });
    }


    /**
     * 截取指定字符
     *
     * @param str
     * @param strStart
     * @return
     */
    public String splitData(String str, String strStart) {
        String tempStr;
        tempStr = str.substring(str.indexOf(strStart) + 1);
        return tempStr;
    }


}
