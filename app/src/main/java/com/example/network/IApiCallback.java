package com.example.network;

import com.example.network.response.errorResponse;
import com.example.network.response.resultResponse;

public interface IApiCallback {
    void onFailure(errorResponse obj);
    void onSuccess(resultResponse obj);
}
