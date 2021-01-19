package com.example.infinitylistwithrxjava.api

import com.example.infinitylist.data.Posts
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RxJavaGetPosts {
    @GET("/posts")
    fun getPosts(): Single<Posts>
}