package com.example.infinitylist.data

import com.google.gson.annotations.SerializedName

class Posts{
    @SerializedName("data")
    var data: List<Post>? = null
}