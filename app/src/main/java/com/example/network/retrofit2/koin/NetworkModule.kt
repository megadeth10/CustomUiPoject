package com.example.network.retrofit2.koin

import com.example.network.networkConstans
import com.example.network.okhttp3.response.TokenResponse
import com.example.store.user.User
import com.example.test.myapplication.BuildConfig
import com.example.utils.Log
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CALL_TIME_OUT = 10L
private const val CONNECT_TIME_OUT = 10L
private const val BASE_URL = "http://10.0.2.2:3000" // android emulator는 localhost가 10.0.2.2이다 "http://127.0.0.1:3000"

/**
 * TODO: refresh Token
 * 1. Okhttp3 authentication: https://square.github.io/okhttp/recipes #Handling authentication
 */
private fun getInterceptor(): HttpLoggingInterceptor {
    val intercepterLevel = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.NONE
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(intercepterLevel)
    return interceptor
}

private class TokenInterceptor(user: User) : Interceptor {
    private val mUser = user
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentToken = mUser.getToken()
        Log.e("TokenInterceptor", "intercept() currentToken : $currentToken")
        val newRequestWithToken = chain.request().newBuilder().header(
                "Authorization", "Bearer $currentToken"
        ).build()

        var response = chain.proceed(newRequestWithToken)
        if (response.code == 555) {
            Log.e("TokenInterceptor", "token expire ${response.code}")
            response.close()
            synchronized(this) {
                Log.e("TokenInterceptor", "token refreshing")
                val newToken = refreshToken(mUser)
                newToken?.let{
                    val newRequestWithNewToken = newRequestWithToken.newBuilder().header(
                            "Authorization", "Bearer $newToken"
                    ).build()
                    response = chain.proceed(newRequestWithNewToken)
                }
            }
        }
        return response
    }
}

fun refreshToken(user: User): String? {
    val client = OkHttpClient.Builder()
            .addInterceptor(getInterceptor())
            .addInterceptor(RefreshTokenInterceptor(user))
            .connectTimeout(CALL_TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .build()
    val request = Request.Builder()
            .url("$BASE_URL/refresh_token")
            .header("Authorization", networkConstans.APP_TOKEN)
            .build()
    var newToken: String? = null
    val response = client.newCall(request).execute()
    if(response.code == 200) {
        val body = response.body
        body?.let {
            val jsonString = it.string()
            Log.e("NetworkModule", "refreshToken() response : $jsonString")
            val tokenResponse = Gson().fromJson<TokenResponse>(jsonString, TokenResponse::class.java)
            newToken = tokenResponse.token
        }
    }
    return newToken
}

private class RefreshTokenInterceptor(user: User) : Interceptor {
    private val mUser = user
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentToken = mUser.getToken()
        Log.e("NetworkModule", "intercept() currentToken : $currentToken")
        val newRequestWithToken = chain.request().newBuilder().header(
                "Authorization", "Bearer $currentToken"
        ).build()
        return chain.proceed(newRequestWithToken)
    }
}

val networkModule = module {
    single {
        Log.e("OkHttpClient", "define")
        OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .authenticator(object : Authenticator {
                    override fun authenticate(route: Route?, response: Response): Request? {
                        Log.e("OkHttpClient", "authenticate() ${route?.address}")
                        return response.request.newBuilder().build()
                    }
                })
                .addInterceptor(TokenInterceptor(get()))
                .connectTimeout(CALL_TIME_OUT, TimeUnit.SECONDS)
                .callTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build()
    }

    single(named("Auth_Http")) {
        // refresh token용
        OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .addInterceptor(RefreshTokenInterceptor(get()))
                .connectTimeout(CALL_TIME_OUT, TimeUnit.SECONDS)
                .callTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build()
    }

    single {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
    }

    single(named("RetrofitRxJava")) {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(get())
                .build()
    }

    single(named("Auth_Retro")) {
        val single = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(get(named("Auth_Http")))
                .build()
    }
}