package com.example.weather_community_android.model

import android.app.Application
import android.content.Intent
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.weather_community_android.activity.MainActivity
import com.example.weather_community_android.repository.UserRepository
import com.google.gson.JsonObject
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable

class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val token : String ?= null
    private val id : ObservableField<String> = ObservableField()
    val myResponse : MutableLiveData<LoginDAO> = MutableLiveData()
    val repo : UserRepository = UserRepository(application)
    fun login(jsonObject : JsonObject) {
        val response = repo.login(jsonObject)
    }

}