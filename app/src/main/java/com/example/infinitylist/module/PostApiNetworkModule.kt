package com.example.infinitylist.module

import com.example.infinitylist.api.GetPosts
import org.koin.dsl.module
import retrofit2.Retrofit

val postApiModule = module {
    single {
        get<Retrofit>().create(GetPosts::class.java)
    }
}