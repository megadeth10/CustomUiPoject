package com.example.network.okhttp3;

import com.example.network.okhttp3.response.errorResponse;
import com.example.network.okhttp3.response.resultResponse;

public interface IOkHttp3ApiCallback {
    void onFailure(errorResponse obj);
    void onSuccess(resultResponse obj);
}
