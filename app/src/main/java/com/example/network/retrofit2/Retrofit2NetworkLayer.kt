package com.example.network.retrofit2

import com.example.test.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ref url: https://square.github.io/retrofit/
 * haeder는 interfaceAPI에 @header annotation으로 정의함.
 */
object Retrofit2NetworkLayer {
    private const val BASE_URL = "http://10.0.2.2:3000" // android emulator는 localhost가 10.0.2.2이다 "http://127.0.0.1:3000"
    private var instance: Retrofit? = null
    private const val CALL_TIME_OUT = 10L
    private const val CONNECT_TIME_OUT= 10L
    fun getInstance(): Retrofit =
            instance ?: synchronized(this) {
                val intercepterLevel = if(BuildConfig.DEBUG){
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(intercepterLevel)
                val client = OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .connectTimeout(CALL_TIME_OUT, TimeUnit.SECONDS)
                        .callTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                        .build()
                this.instance ?: Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
            }
}