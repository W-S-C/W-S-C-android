package com.example.weather_community_android.network

import androidx.constraintlayout.widget.Group
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

//    private lateinit var retrofit: Retrofit
    private const val BASE_URL = "http://52.78.183.55:8080"
    val gson : Gson = GsonBuilder()
            .setLenient()
            .create()
    private val retrofit: Retrofit by lazy{
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

    }

    val loginService : LoginApi by lazy {
        retrofit.create(LoginApi::class.java)
    }



}