package com.example.infinitylistwithrxjava.module

import com.example.infinitylistwithrxjava.api.RxJavaGetPosts
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val postRxJavaApiModule = module {
    single {
        get<Retrofit>(named("RetrofitRxJava")).create(RxJavaGetPosts::class.java)
    }
}