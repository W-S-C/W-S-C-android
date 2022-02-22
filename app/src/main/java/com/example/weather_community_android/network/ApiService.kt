package com.example.weather_community_android.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

//    private lateinit var retrofit: Retrofit
    private const val BASE_URL = "http://localhost:8070/"
    private val retrofit: Retrofit by lazy{

        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    val loginService : LoginApi by lazy {
        retrofit.create(LoginApi::class.java)
    }



}