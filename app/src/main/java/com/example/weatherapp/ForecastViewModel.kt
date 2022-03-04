package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val service : Api) : ViewModel() {
    private val _forecasts = MutableLiveData<Forecast>()
    val forecasts : LiveData<Forecast>
        get() = _forecasts


    fun loadData() = runBlocking{
        launch {
            _forecasts.value = service.getForecast("55127")
        }
    }
}