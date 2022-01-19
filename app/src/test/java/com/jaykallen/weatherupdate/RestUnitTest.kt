package com.jaykallen.weatherupdate

import com.jaykallen.weatherupdate.internet.ApiService
import com.jaykallen.weatherupdate.internet.RestManager
import com.jaykallen.weatherupdate.model.WeatherModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Call
import strikt.api.expectThat


class RestUnitTest {
    @Test
    fun `it should GET with query`() {
        val query = "Houston,TX,US"
        val request: RecordedRequest = mockRequest {
            queryWeather(query, "XXXX")
        }

        expectThat(request) {
            assertThat("is GET method") {
                it.method == "GET"
            }
            assertThat("has given search query") {
                it.requestUrl?.queryParameterValues("search") == listOf(query)
            }
        }
    }

    private fun mockRequest(sut: ApiService.() -> Unit): RecordedRequest {
        return MockWebServer()
            .use {
                it.enqueue(MockResponse())
                it.start()
                val url = it.url("/")
                sut(RestManager.initiateRetrofit())
                it.takeRequest()
            }
    }

}