package com.example.activity.infinitylist

import com.example.activity.infinitylist.item.InfinityItem
import retrofit2.Call
import retrofit2.http.GET

interface Retrofit2APIInterface {
    @GET("posts")
    fun getPosts():Call<InfinityItem>
}