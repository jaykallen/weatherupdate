package com.jaykallen.weatherupdate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jaykallen.weatherupdate.internet.RestManager
import com.jaykallen.weatherupdate.model.WeatherModel

class HomeViewModel : ViewModel() {
    val liveData: LiveData<WeatherModel>
        get() = RestManager.mutableLiveData

    fun calcK2F(kelvin: String): String? {
        // Calculation Kelvin to Fahrenheit.  Example formula: 300K × 9/5 - 459.67 = 80.33 °F
        val k = kelvin.toDouble()
        val f = k * (9.0 / 5.0) - 459.67
        return String.format("%.0f", f) + ""
    }
}