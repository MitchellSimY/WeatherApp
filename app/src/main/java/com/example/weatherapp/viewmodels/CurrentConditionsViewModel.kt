package com.example.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.api.Api
import com.example.weatherapp.models.CurrentConditions
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CurrentConditionsViewModel @Inject constructor(private val service: Api) : ViewModel() {

    private val _currentConditions = MutableLiveData<CurrentConditions>()
    val currentConditions: LiveData<CurrentConditions>
        get() = _currentConditions

    fun loadData(zipCode: String?, lat: String, long: String) = runBlocking {

        if (zipCode == null) {
            launch {
                _currentConditions.value = service.getCurrentConditions(
                    "",
                    lat,
                    long)
            }
        } else {
            launch {
                _currentConditions.value = service.getCurrentConditions(zipCode.toString())
            }
        }
    }

}