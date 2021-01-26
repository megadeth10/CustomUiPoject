package com.example.scene.infinitylist.module

import com.example.scene.infinitylist.api.GetPosts
import org.koin.dsl.module
import retrofit2.Retrofit

val postApiModule = module {
    single {
        get<Retrofit>().create(GetPosts::class.java)
    }
}