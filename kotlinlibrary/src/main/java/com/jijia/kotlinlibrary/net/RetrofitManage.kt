package com.jijia.kotlinlibrary.net

import android.util.Log
import com.jijia.kotlinlibrary.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManage {

    lateinit var retrofit: Retrofit

    // 日志拦截器
    private val mLoggingInterceptor: Interceptor by lazy {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("BaseAndroid", message)
            }

        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // OkHttpClient客户端
    private val mClient: OkHttpClient by lazy { newClient() }

    fun initRetrofit(baseUrl: String) {
        retrofit = Retrofit.Builder().baseUrl(baseUrl).client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * OkHttpClient客户端
     */
    private fun newClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)// 连接时间：30s超时
        readTimeout(10, TimeUnit.SECONDS)// 读取时间：10s超时
        writeTimeout(10, TimeUnit.SECONDS)// 写入时间：10s超时
        if (BuildConfig.DEBUG)
            addInterceptor(mLoggingInterceptor)// 仅debug模式启用日志过滤器
    }.build()
}