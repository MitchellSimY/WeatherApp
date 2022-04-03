package com.example.weatherapp.api

import com.example.weatherapp.models.CurrentConditions
import com.example.weatherapp.models.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    // This is making a GET request.
    @GET("weather")
    suspend fun getCurrentConditions(
        @Query("zip") zip: String = "55127",
        @Query("lat") lat: String? = "",
        @Query("lon") long: String? = "",
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "127b4de6be5116abbdbaa8296a35f7e1",

        ): CurrentConditions

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zip: String = "55127",
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "127b4de6be5116abbdbaa8296a35f7e1",
        @Query("cnt") cnt: String = "16"

    ): Forecast


}