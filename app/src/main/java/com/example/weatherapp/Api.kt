package com.example.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    // This is making a GET request.
    @GET("weather")
    fun getCurrentConditions(
        @Query("zip") zip: String = "55127",
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "127b4de6be5116abbdbaa8296a35f7e1",

        ) : Call<CurrentConditions>

    @GET("daily")
    fun getForecast(
        @Query("zip") zip: String = "55127",
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = "127b4de6be5116abbdbaa8296a35f7e1",
        @Query("cnt") cnt: String = "16"

        ) : Call<Forecast>
}