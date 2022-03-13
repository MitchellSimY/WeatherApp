package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.RecyclerViewBinding
import com.example.weatherapp.databinding.RowDateBinding
import com.example.weatherapp.viewmodels.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.row_date) {
    // private lateinit var recyclerView: RecyclerView
    private lateinit var binding: RowDateBinding

    @Inject
    lateinit var viewModel : ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RowDateBinding.inflate(layoutInflater)

        viewModel.forecasts.observe(this){
            // Assigning forecasts variable in the viewModel
                forecasts -> ForecastAdapter(forecasts.list)

            // Then this gives the adapter the list of data
//            recyclerView.adapter = ForecastAdapter(forecasts.list)
        }
        viewModel.loadData()
    }

    override fun onResume() {
        super.onResume()
    }
}
