package com.example.user.test.Rt;

import com.google.gson.annotations.SerializedName;

/**
 * 请求返回成功时需要解析的数据
 */
class RxText {
    @SerializedName("login")
    public String login;
    @SerializedName("id")
    public int id;
}
