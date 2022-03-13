package com.example.weatherapp.models

data class CurrentConditions(
    val weather: List<WeatherCondition>,
    val main: Currents,
    val name: String,

    )
