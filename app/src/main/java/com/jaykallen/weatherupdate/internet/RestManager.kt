package com.jaykallen.weatherupdate.internet

import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.jaykallen.weatherupdate.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestManager {
    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/?"
    var mutableLiveData = MutableLiveData<WeatherModel>()

    fun getWeather(input: String) {
            val service = initiateRetrofit()
            val call = service.queryWeather(input, appid="4003650e8ddff5c5047708b14c8ed741")
            println("Attempting URL: " + call?.request()?.url())
            call?.enqueue(object : Callback<WeatherModel?> {
                override fun onResponse(call: Call<WeatherModel?>, response: Response<WeatherModel?>) {
                    println("Successful Query: " + response.body())
                    val weather = response.body()
                    if (weather != null) {
                        mutableLiveData.value = weather
                    }
                }
                override fun onFailure(call: Call<WeatherModel?>, t: Throwable) {
                    println("Failed Call: $t")
                }
            })
    }

    fun initiateRetrofit(): ApiService {
        val gSon = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .build()
        return retrofit.create(ApiService::class.java)
    }

}