package com.example.network.okhttp3;

import com.example.network.networkConstans;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttp3NetworkLayer {
    static private OkHttpClient newInstance = null;
    private OkHttp3NetworkLayer(){
        if(OkHttp3NetworkLayer.newInstance == null){
            OkHttp3NetworkLayer.newInstance = new OkHttpClient();
        }
    }

    public static OkHttpClient getInstance () {
        if(OkHttp3NetworkLayer.newInstance == null){
            OkHttp3NetworkLayer.newInstance = new OkHttpClient();
        }
        return OkHttp3NetworkLayer.newInstance;
    }

    static public void getAppInfo (IOkHttp3ApiCallback apiCallback, Class<?> parseObject) {
        Request request = new Request.Builder()
                .url(networkConstans.COMMON_URL+ "/v1/appinfo")
                .header("Authorization", networkConstans.APP_TOKEN)
                .build();
        OkHttp3ResponseCallback callback = new OkHttp3ResponseCallback(apiCallback, parseObject);
        OkHttp3NetworkLayer.getInstance().newCall(request).enqueue(callback);
    }
}
