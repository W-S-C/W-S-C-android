package com.example.weather_community_android.model

data class TempClass (val response: TempResponse)
data class TempResponse (val header : TempHeader, val body : TempBody)
data class TempHeader (val resultCode : Int, val resultMsg : String)
data class TempBody (val dataType : String, val items : TempItems)
data class TempItems(val item : List<TempItem>)
data class TempItem(val category : String, val fcstDate : String, val fcstTime : String, val fcstValue : String)