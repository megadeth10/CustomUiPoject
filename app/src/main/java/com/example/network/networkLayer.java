package com.example.network;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class networkLayer{
    static private OkHttpClient newInstance = null;
    private networkLayer(){
        if(networkLayer.newInstance == null){
            networkLayer.newInstance = new OkHttpClient();
        }
    }

    public static OkHttpClient getInstance () {
        if(networkLayer.newInstance == null){
            networkLayer.newInstance = new OkHttpClient();
        }
        return networkLayer.newInstance;
    }

    static public void getAppInfo (IApiCallback apiCallback, Class<?> parseObject) {
        Request request = new Request.Builder()
                .url(networkConstans.COMMON_URL+ "/v1/appinfo")
                .header("Authorization", networkConstans.APP_TOKEN)
                .build();
        responseCallback callback = new responseCallback(apiCallback, parseObject);
        networkLayer.getInstance().newCall(request).enqueue(callback);
    }
}
