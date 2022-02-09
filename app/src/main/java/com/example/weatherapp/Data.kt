package com.example.weatherapp

data class Data(
    val date: Long,
    val sunriseTime: Long, val sunsetTime: Long,
    val temp: ForecastTemp, val pressure: Float,
    val humidity: Int,
) {

    class ForecastTemp(day: Float, min: Float, max: Float) {
        val day = day;
        val min = min;
        val max = max;
    }

}

