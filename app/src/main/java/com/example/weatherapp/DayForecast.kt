package com.example.weatherapp

data class DayForecast(
    val date: Long,
    val sunriseTime: Long, val sunsetTime: Long,
    val temp: ForecastTemp, val pressure: Float,
    val humidity: Int,
) {

}

