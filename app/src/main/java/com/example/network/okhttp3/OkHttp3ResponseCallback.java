package com.example.network.okhttp3;

import com.example.network.okhttp3.response.errorResponse;
import com.example.network.okhttp3.response.resultResponse;
import com.example.utils.Log;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OkHttp3ResponseCallback implements Callback {
    private IOkHttp3ApiCallback mApiCallback;
    private Class<?> parseObject;

    public OkHttp3ResponseCallback(IOkHttp3ApiCallback apiCallback, Class<?> obj){
        this.mApiCallback = apiCallback;
        this.parseObject = obj;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {

    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        long startTime = System.nanoTime();
        int resultCode = response.code();
        String message = response.message();
        String body = response.body().string();
        try {
            Log.i(OkHttp3ResponseCallback.class.getSimpleName(), Integer.toString(response.code()));
            Log.i(OkHttp3ResponseCallback.class.getSimpleName(), body);
            Gson gson = new Gson();
            resultResponse result = (resultResponse) gson.fromJson(body, parseObject);
            result.setCode(resultCode);
            result.setMessage(message);
            if (mApiCallback != null) {
                mApiCallback.onSuccess(result);
            }
            long time = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            Log.e(OkHttp3ResponseCallback.class.getSimpleName(), String.format("시간값 %d", time));
        }catch (Exception e) {
            Log.e(OkHttp3ResponseCallback.class.getSimpleName(), e.getMessage());
            if(mApiCallback != null) {
                Gson gson = new Gson();
                errorResponse result = gson.fromJson(body, errorResponse.class);
                mApiCallback.onFailure(result);
            }
        }
    }
}
