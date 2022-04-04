package com.example.weatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.api.Api
import com.example.weatherapp.models.Forecast
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val service: Api) : ViewModel() {
    private val _forecasts = MutableLiveData<Forecast>()
    val forecasts: LiveData<Forecast>
        get() = _forecasts


    fun loadData(zipCode: String?, lat: String?, long: String?) = runBlocking {

        if (zipCode == null) {
            launch {
                _forecasts.value = service.getForecast(
                    "",
                    lat,
                    long
                )
            }
        } else {
            launch {
                _forecasts.value = service.getForecast(zipCode.toString())
            }
        }



    }
}