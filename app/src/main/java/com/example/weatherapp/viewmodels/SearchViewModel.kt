package com.example.weatherapp.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.fragments.CurrentConditionsFragment
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchViewModel: ViewModel() {
    private val _enableButton = MutableLiveData(false)

    private var zipCode : String? = null
    private var longitude : String? = null
    private var latitude : String? = null

    val enableButton : LiveData<Boolean>
        get() = _enableButton

    fun updateZipCode(zipCode: String) {
        if (zipCode != this.zipCode) {
            this.zipCode = zipCode
            _enableButton.value = isValidZipCode(zipCode)
        }
    }

    fun updateLatLong(lat : String?, long : String?) {
        if (lat != this.latitude && long != this.longitude) {
            this.latitude
        }
    }

    private fun isValidZipCode(zipCode: String): Boolean? {
        return zipCode.length == 5 && zipCode.all {
            it.isDigit()
        }
    }

}