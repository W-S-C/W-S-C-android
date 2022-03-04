package com.example.weather_community_android.model

data class WeatherClass (val response: WeatherResponse)
data class WeatherResponse (val header : WeatherHeader, val body : WeatherBody)
data class WeatherHeader (val resultCode : Int, val resultMsg : String)
data class WeatherBody (val dataType : String, val items : WeatherItems, val totalCount : Int)
data class WeatherItems(val item : List<WeatherItem>)
data class WeatherItem(val category : String, val fcstDate : String, val fcstTime : String, val fcstValue : String)