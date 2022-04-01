package com.example.weatherapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ForecastTemp(val day: Float, val min: Float, val max: Float) : Parcelable {
//    Did I do this correctly?
//    val day = day;
//    val min = min;
//    val max = max;
}
