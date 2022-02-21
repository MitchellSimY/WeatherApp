package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private val apiKey = "127b4de6be5116abbdbaa8296a35f7e1"

    private lateinit var api: Api
    private lateinit var cityName: TextView
    private lateinit var currentTemp: TextView
    private lateinit var feelsLike: TextView
    private lateinit var lowTemp: TextView
    private lateinit var highTemp: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView
    private lateinit var conditionIcon: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityName = findViewById(R.id.cityName)
        currentTemp = findViewById(R.id.temperature)
        feelsLike = findViewById(R.id.feelsLike)
        lowTemp = findViewById(R.id.low)
        highTemp = findViewById(R.id.high)
        humidity = findViewById(R.id.humidity)
        pressure = findViewById(R.id.pressure)
        conditionIcon = findViewById(R.id.conditionIcon)

        val forecastBtn = findViewById<Button>(R.id.forecastButton)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        api = retrofit.create(Api::class.java)

        forecastBtn.setOnClickListener(View.OnClickListener {
            val forecastIntent = Intent(this, ForecastActivity::class.java)
            startActivity(forecastIntent)
        })
    }

    override fun onResume() {
        super.onResume()

        val call: Call<CurrentConditions> = api.getCurrentConditions("32789")
        call.enqueue(object: Callback<CurrentConditions> {
            override fun onResponse(
                call: Call<CurrentConditions>,
                response: Response<CurrentConditions>
            ) {
                val currentConditions = response.body()

                currentConditions?.let {
                    bindData(it)
                }
            }

            override fun onFailure(call: Call<CurrentConditions>, t: Throwable) {

            }


        })
    }

    private fun bindData(currentConditions: CurrentConditions) {
        cityName.text = currentConditions.name
        currentTemp.text = getString(R.string.temperature, currentConditions.main.temp.toInt())
        feelsLike.text = getString(R.string.feelsLike, currentConditions.main.feelsLike.toInt())
        lowTemp.text = getString(R.string.low, currentConditions.main.tempMin.toInt())
        highTemp.text = getString(R.string.high, currentConditions.main.tempMax.toInt())
        pressure.text = getString(R.string.pressure, currentConditions.main.pressure.toInt())
        humidity.text = getString(R.string.humidity, currentConditions.main.humidity.toInt())

        // How to get the icon to change
        val iconName = currentConditions.weather.firstOrNull()?.icon;
        val iconUrl = "https://openweathermap.org/img/wn/${iconName}@2x.png"
        Glide.with(this)
            .load(iconUrl)
            .into(conditionIcon)
    }
}