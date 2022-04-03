package com.example.weather_community_android.model

data class LoginDAO (
        var statusCode : Int,
        var responseMessage : String,
        var data : String
        )
data class LoginEntity(
        val id : String,
        var pwd : String
)