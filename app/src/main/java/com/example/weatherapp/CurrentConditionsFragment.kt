package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.CurrentconditionsBinding
import com.example.weatherapp.models.CurrentConditions
import com.example.weatherapp.viewmodels.CurrentConditionsViewModel
import javax.inject.Inject

class CurrentConditionsFragment : Fragment(R.layout.currentconditions) {

    private lateinit var binding : CurrentconditionsBinding
    @Inject
    lateinit var viewModel: CurrentConditionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currentconditions, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = CurrentconditionsBinding.inflate(layoutInflater)

//        viewModel.currentConditions.

//        viewModel.currentConditions.observe(this) {
//                currentConditions -> bindData(currentConditions)
//        }
//        viewModel.loadData()

        // bindData(currentConditions)

        binding.forecastButton.setOnClickListener() {
            replaceFragment(ForecastFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment) {

    }


    private fun bindData(currentConditions: CurrentConditions) {
        binding.cityName.text = currentConditions.name
        binding.temperature.text = getString(R.string.temperature, currentConditions.main.temp.toInt())
        binding.feelsLike.text = getString(R.string.feelsLike, currentConditions.main.feelsLike.toInt())
        binding.low.text = getString(R.string.low, currentConditions.main.tempMin.toInt())
        binding.high.text = getString(R.string.high, currentConditions.main.tempMax.toInt())
        binding.pressure.text = getString(R.string.pressure, currentConditions.main.pressure.toInt())
        binding.humidity.text = getString(R.string.humidity, currentConditions.main.humidity.toInt())

        // How to get the icon to change
        val iconName = currentConditions.weather.firstOrNull()?.icon;
        val iconUrl = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconUrl)
            .into(binding.conditionIcon)
    }

}