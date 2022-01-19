package com.jaykallen.weatherupdate

import com.jaykallen.weatherupdate.internet.ApiService
import com.jaykallen.weatherupdate.internet.RestManager
import com.jaykallen.weatherupdate.internet.RestManager.initiateRetrofit
import com.jaykallen.weatherupdate.model.WeatherModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Call
import strikt.api.expectThat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}