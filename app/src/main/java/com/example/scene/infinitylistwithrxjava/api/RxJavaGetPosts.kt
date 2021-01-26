package com.example.scene.infinitylistwithrxjava.api

import com.example.scene.infinitylist.data.Posts
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RxJavaGetPosts {
    @GET("/posts")
    fun getPosts(): Single<Posts>
}