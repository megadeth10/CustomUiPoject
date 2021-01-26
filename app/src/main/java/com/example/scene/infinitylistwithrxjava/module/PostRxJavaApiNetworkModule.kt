package com.example.scene.infinitylistwithrxjava.module

import com.example.scene.infinitylistwithrxjava.api.RxJavaGetPosts
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val postRxJavaApiModule = module {
    single {
        get<Retrofit>(named("RetrofitRxJava")).create(RxJavaGetPosts::class.java)
    }
}