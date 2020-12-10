package com.example.network;

import android.util.Log;

import com.example.network.response.errorResponse;
import com.example.network.response.resultResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class responseCallback implements Callback {
    private IApiCallback mApiCallback;
    private Class<?> parseObject;

    public responseCallback(IApiCallback apiCallback, Class<?> obj){
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
            Log.i(responseCallback.class.getSimpleName(), Integer.toString(response.code()));
            Log.i(responseCallback.class.getSimpleName(), body);
            Gson gson = new Gson();
            resultResponse result = (resultResponse) gson.fromJson(body, parseObject);
            result.setCode(resultCode);
            result.setMessage(message);
            if (mApiCallback != null) {
                mApiCallback.onSuccess(result);
            }
            long time = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
            Log.e(responseCallback.class.getSimpleName(), String.format("시간값 %d", time));
        }catch (Exception e) {
            Log.e(responseCallback.class.getSimpleName(), e.getMessage());
            if(mApiCallback != null) {
                Gson gson = new Gson();
                errorResponse result = gson.fromJson(body, errorResponse.class);
                mApiCallback.onFailure(result);
            }
        }
    }
}
