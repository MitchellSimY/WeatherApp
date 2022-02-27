package com.example.weatherapp

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ForecastViewModel @Inject constructor(private val service : Api) : ViewModel() {


}