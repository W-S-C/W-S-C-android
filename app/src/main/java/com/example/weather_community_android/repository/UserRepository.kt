package com.example.weather_community_android.repository

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.weather_community_android.activity.LoginActivity
import com.example.weather_community_android.activity.MainActivity
import com.example.weather_community_android.model.LoginDAO
import com.example.weather_community_android.network.ApiService
import com.example.weather_community_android.network.LoginApi
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import java.util.*

class UserRepository(application: Application) {

    private val loginApi = ApiService.loginService
    private var token: String ?= null
    private var loginDAO : LoginDAO ?= null
    private var response: Response<LoginDAO> ?= null
    fun login(inputJson:JsonObject) : @NonNull Disposable? {
       return loginApi.login(inputJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response: LoginDAO ->
                    Log.d("login", response.data)

                }, {
                    error: Throwable ->
                    Log.d("login error", error.message)
                })
    }
}