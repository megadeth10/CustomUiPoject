package com.example.scene.infinitylist.api

import com.example.scene.infinitylist.data.Posts
import retrofit2.Call
import retrofit2.http.GET

interface Retrofit2APIInterface {
    @GET("posts")
    fun getPosts():Call<Posts>
}