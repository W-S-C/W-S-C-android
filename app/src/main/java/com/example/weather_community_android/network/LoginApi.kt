package com.example.weather_community_android.network

import com.example.weather_community_android.model.LoginDAO
import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Single
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/user/login")
    fun login(@Body body: JsonObject) : Single<LoginDAO>
}