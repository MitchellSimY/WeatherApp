package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityName = findViewById(R.id.cityName)
        currentTemp = findViewById(R.id.temperature)

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

        val call: Call<CurrentConditions> = api.getCurrentConditions("55127")
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
//        currentTemp.text = getString(R.string.temperature, currentConditions.main.temp.toInt());
        currentTemp.text = getString(R.string.temperature, currentConditions.main.temp.toInt())
    }
}