package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForecastActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val adapterData = listOf<DayForecast>(
        DayForecast(1643922823, 1643875200, 1643846940, ForecastTemp(19f, 24f, 17f), 0.0f, 0),
        DayForecast(1644009223, 1643933340, 1644001260, ForecastTemp(20f, 25f, 18f), 0.0f, 0),
        DayForecast(1644095623, 1644048060, 1644087720, ForecastTemp(15f, 20f, 10f), 0.0f, 0),
        DayForecast(1644182023, 1644134520, 1644174120, ForecastTemp(10f, 15f, 8f), 0.0f, 0),
        DayForecast(1644268423, 1644220980, 1644260580, ForecastTemp(23f, 25f, 20f), 0.0f, 0),
        DayForecast(1644354823, 1644307440, 1644347040, ForecastTemp(30f, 33f, 27f), 0.0f, 0),
        DayForecast(1644441223, 1644393900, 1644433500, ForecastTemp(-14f, 1f, -15f), 0.0f, 0),
        DayForecast(1644527623, 1644480360, 1644519960, ForecastTemp(13f, 20f, 10f), 0.0f, 0),
        DayForecast(1644614023, 1644566700, 1644538140, ForecastTemp(24f, 25f, 23f), 0.0f, 0),
        DayForecast(1644786823, 1644653160, 1644692760, ForecastTemp(13f, 13f, 10f), 0.0f, 0),
        DayForecast(1644873223, 1644739620, 1644779220, ForecastTemp(30f, 31f, 28f), 0.0f, 0),
        DayForecast(1644959623, 1644797340, 1644865800, ForecastTemp(31f, 30f, 25f), 0.0f, 0),
        DayForecast(1645046023, 1644912540, 1644952260, ForecastTemp(27f, 33f, 27f), 0.0f, 0),
        DayForecast(1645132423, 1644999000, 1645038720, ForecastTemp(25f, 29f, 22f), 0.0f, 0),
        DayForecast(1645218823, 1645142940, 1645056540, ForecastTemp(27f, 29f, 10f), 0.0f, 0),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ForecastAdapter(adapterData);
    }
}