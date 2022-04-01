package com.example.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.api.Api
import com.example.weatherapp.databinding.CurrentconditionsBinding
import com.example.weatherapp.models.CurrentConditions

import com.example.weatherapp.viewmodels.CurrentConditionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class CurrentConditionsFragment : Fragment(R.layout.currentconditions) {

    private val args: CurrentConditionsFragmentArgs by navArgs()
    private lateinit var binding: CurrentconditionsBinding
    private lateinit var api: Api
//    private lateinit var zipCode: ZipCode

    @Inject
    lateinit var viewModel: CurrentConditionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // val view = inflater.inflate(R.layout.currentconditions, container, false)
        val zipCodeData = args.zipCodeArgument


        binding = CurrentconditionsBinding.inflate(layoutInflater)

        viewModel?.currentConditions?.observe(viewLifecycleOwner) { currentConditions ->
            bindData(currentConditions)
        }

        try {
            zipCodeData?.let { viewModel?.loadData(it) }
        } catch (e: HttpException) {
            Log.d("API Call error: ", e.toString())
        }


        // When button is clicked, go here
        binding.forecastButton.setOnClickListener() {
            val action = SearchFragmentDirections.navCurrentConditionsToForecast(zipCodeData)
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }
// https://stackoverflow.com/questions/58964019/lateinit-property-homeviewmodel-has-not-been-initialized

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().title = "Current Conditions"

    }

    private fun bindData(currentConditions: CurrentConditions) {
        binding.cityName.text = currentConditions.name
        binding.temperature.text =
            getString(R.string.temperature, currentConditions.main.temp.toInt())
        binding.feelsLike.text =
            getString(R.string.feelsLike, currentConditions.main.feelsLike.toInt())
        binding.low.text = getString(R.string.low, currentConditions.main.tempMin.toInt())
        binding.high.text = getString(R.string.high, currentConditions.main.tempMax.toInt())
        binding.pressure.text =
            getString(R.string.pressure, currentConditions.main.pressure.toInt())
        binding.humidity.text =
            getString(R.string.humidity, currentConditions.main.humidity.toInt())

        // How to get the icon to change
        val iconName = currentConditions.weather.firstOrNull()?.icon;
        val iconUrl = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconUrl)
            .into(binding.conditionIcon)
    }

}