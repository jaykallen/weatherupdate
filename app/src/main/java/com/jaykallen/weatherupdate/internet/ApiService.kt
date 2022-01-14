package com.jaykallen.weatherupdate.internet


import com.jaykallen.weatherupdate.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    abstract fun queryWeather(@Query("q") city: String?, @Query("appid") appid: String?): Call<WeatherModel?>?
}