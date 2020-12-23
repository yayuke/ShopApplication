package com.yyk.shopapplication.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetUtils {
    //    使用okhttp进行网络请求
    public static void doGet(Callback callback) {
//        创建okhttp客户端
        OkHttpClient client = new OkHttpClient();
        //创建请求对象,默认get请求
        Request build = new Request.Builder().url(Constant.path).build();
//        异步请求,okhttp会自动创造子线程进行网络请求
        client.newCall(build).enqueue(callback);
    }
}
