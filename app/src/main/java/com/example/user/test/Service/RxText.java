package com.example.user.test.Service;

import com.google.gson.annotations.SerializedName;

/**
 * 请求返回成功时需要解析的数据
 */
public class RxText {
    @SerializedName("login")
    public String login;
    @SerializedName("id")
    public int id;
}
