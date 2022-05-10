package com.kula.growthchannelapi.utils;


import cn.hutool.http.HttpRequest;

import java.util.Map;

public class HttpUtil {

    public static String buildGetUrl(String url, Map<String, String> params) {
        StringBuilder str = new StringBuilder();
        params.forEach((k, v) -> {
            str.append("&");
            str.append(k);
            str.append("=");
            str.append(v);
        });
        str.replace(0, 1, "?");
        return url + str;
    }

    public static String postForm(String url, Map<String, Object> params) {
        return HttpRequest.post("https://oauth2.googleapis.com/token")
                .form(params)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
    }
}
