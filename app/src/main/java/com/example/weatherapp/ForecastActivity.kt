package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForecastActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val adapterData = listOf<Data>(
        Data(1643922823, 19, 24,17,"6:00PM","8:00AM"),
        Data(1644009223, 20, 25, 18,"6:01PM","8:01AM"),
        Data(1644095623, 15, 20,10,"6:01PM","8:01AM"),
        Data(1644182023, 10,15,8,"6:02PM","8:02AM"),
        Data(1644268423, 23,25,20,"6:02PM","8:02AM"),
        Data(1644354823, 30,33,27,"6:03PM","8:02AM"),
        Data(1644441223, -14,1,-15,"6:04PM","8:03AM"),
        Data(1644527623, 13,20,10,"6:04pM","8:04AM"),
        Data(1644614023, 24,25,23,"6:05PM","8:05AM"),
        Data(1644786823, 13,13,10,"6:06PM","8:06AM"),
        Data(1644873223, 30,31,28,"6:07PM","8:06AM"),
        Data(1644959623, 31,30,25,"6:08PM","8:06AM"),
        Data(1645046023, 27,33,27,"6:09PM","8:07AM"),
        Data(1645132423, 25,29,22,"6:10PM","8:08AM"),
        Data(1645218823, 27,29,10,"6:10PM","8:09AM"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ForecastAdapter(adapterData);
    }
}