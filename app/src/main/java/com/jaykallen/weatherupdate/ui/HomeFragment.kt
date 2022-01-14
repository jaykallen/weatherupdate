package com.jaykallen.weatherupdate.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaykallen.weatherupdate.R
import com.jaykallen.weatherupdate.StartApp
import com.jaykallen.weatherupdate.internet.RestManager
import com.jaykallen.weatherupdate.model.WeatherModel
import com.jaykallen.weatherupdate.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

// todo Add unit tests

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("***************** Home Fragment *******************")
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        homeViewModel.observeLiveData(this)
        observeData()
        setupButtons(requireView())
    }

    private fun setupButtons(view: View) {
        view.findViewById<Button>(R.id.weatherButton).setOnClickListener {
            getEntry()
        }
    }

    fun getEntry() {
        val city: String = cityEdit.getText().toString()
        Timber.d("You entered " + city)
        val parsed = city.replace("\\s+".toRegex(), "") // Move to View Model
        getWeather(parsed)
    }

    private fun observeData() {
        homeViewModel.liveData.observe(viewLifecycleOwner, Observer { weatherData ->
            println("Team Data received $weatherData")
            updateUI(weatherData)
        })
    }

    private fun updateUI(weatherData: WeatherModel) {
        // I would have a function in here that would hide / unhide the weather data field (so Sharknado doesn't appear - lol)
        if (weatherData != null) {
            Timber.d("Weather Received: " + weatherData.id)
            weatherText.setText("Forecast: " + weatherData.weather?.get(0)?.description)
            currTempText.setText("Current Temp: " + homeViewModel.calcK2F(weatherData.main?.temp.toString() + "").toString() + " F")
            tempRangeText.setText(
                "High " + homeViewModel.calcK2F(weatherData.main?.tempMax.toString() + "")
                    .toString() + " F, Low " + homeViewModel.calcK2F(weatherData.main?.tempMin.toString() + "").toString() + " F"
            )
            displayIcon(weatherData)
        } else {
            Timber.d("Weather is returning null for some reason")
            Toast.makeText(activity, "No data found for the city", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayIcon(weatherData: WeatherModel) {
        val iconUrl = "http://openweathermap.org/img/w/" + weatherData.weather?.get(0)?.icon.toString() + ".png"
        Timber.d("Icon for display is $iconUrl")
        val builder = Picasso.Builder(StartApp.applicationContext())
        builder.listener(fun(picasso: Picasso, uri: Uri, exception: Exception) {
            exception.printStackTrace()
        })
        builder.build().load(iconUrl).into(weatherIcon)
    }

    private fun getWeather(input: String) {
        println("Get Weather Initiated")
        RestManager.getWeather(input)
    }
}
