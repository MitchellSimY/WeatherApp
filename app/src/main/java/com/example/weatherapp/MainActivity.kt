package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityForecastBinding
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.RowDateBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.forecastButton.setOnClickListener(View.OnClickListener {
            val forecastIntent = Intent(this, ForecastActivity::class.java)
            startActivity(forecastIntent)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.currentConditions.observe(this) {
            currentConditions -> bindData(currentConditions)
        }
        viewModel.loadData();
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