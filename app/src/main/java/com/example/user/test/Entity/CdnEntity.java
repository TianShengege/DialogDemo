package com.example.user.test.Entity;

import com.example.user.test.Constant.Constant;
/**
 * Created by TianShen on 2018/9/10.
 */
public class CdnEntity {
    private String cdn;
    private String client_ip;
    private String url;
    private int response_time;
    private int bodysize;
    private int status_code;

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getResponse_time() {
        return response_time;
    }

    public void setResponse_time(int response_time) {
        this.response_time = response_time;
    }

    public int getBodysize() {
        return bodysize;
    }

    public void setBodysize(int bodysize) {
        this.bodysize = bodysize;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }


    public String CDN_URL() {
        String cdn_url = Constant.CDN_URL_REQUEST +
                "cdn=" + cdn + "&" +
                "url=" + url + "&" +
                "bodysize=" + bodysize + "&" +
                "response_time=" + response_time + "&" +
                "status_code=" + status_code+ "&" +
        "client_ip=" + client_ip;
        return cdn_url;
    }
}
