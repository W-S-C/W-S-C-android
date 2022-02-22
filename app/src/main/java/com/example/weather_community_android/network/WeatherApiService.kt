package com.example.weather_community_android.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("getUltraSrtFcst?serviceKey=" +
            "EcLob43qb7gwVVyd4VFWi9f5CBgcDBrcv1a9ixVuJ4rQ9%2F2AJw8qnXtFKCCD%2BNY1rftGzxho%2FmUqP%2BxdRD5wSw%3D%3D")
    fun GetWeather(@Query("numOfRows") num_of_rows : Int,
                   @Query("pageNo") page_no : Int,
                   @Query("dataType") data_type : String,
                   @Query("base_date") base_date : String,
                   @Query("base_time") base_time : String,
                   @Query("nx") nx : String,
                   @Query("ny") ny : String)
                   : Call<WEATHER>

}

data class WEATHER (val response: RESPONSE)
data class RESPONSE (val header : HEADER, val body : BODY)
data class HEADER (val resultCode : Int, val resultMsg : String)
data class BODY (val dataType : String, val items : ITEMS, val totalCount : Int)
data class ITEMS(val item : List<ITEM>)
data class ITEM(val category : String, val fcstDate : String, val fcstTime : String, val fcstValue : String)

private val retrofit = Retrofit.Builder()
        .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

object ApiObject{
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

