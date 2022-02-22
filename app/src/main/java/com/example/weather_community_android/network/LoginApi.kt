package com.example.weather_community_android.network

import io.reactivex.Single
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("user/login")
    fun login(@Body body : JSONObject) : Single<LoginApi>
}