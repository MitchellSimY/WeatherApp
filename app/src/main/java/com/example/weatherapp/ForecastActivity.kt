package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.viewmodels.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var viewModel : ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.forecasts.observe(this){
            // Assigning forecasts variable in the viewModel
            forecasts -> ForecastAdapter(forecasts.list)

            // Then this gives the adapter the list of data
            recyclerView.adapter = ForecastAdapter(forecasts.list)
        }
        viewModel.loadData()
    }

    override fun onResume() {
        super.onResume()
    }
}