package com.example.network.okhttp3.response

import com.google.gson.annotations.SerializedName

class TokenResponse {
    @SerializedName("token")
    var token: String = ""
}