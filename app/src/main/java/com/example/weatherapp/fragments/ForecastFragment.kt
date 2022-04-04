package com.example.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.ForecastAdapter
import com.example.weatherapp.R
import com.example.weatherapp.databinding.RecyclerViewBinding
import com.example.weatherapp.viewmodels.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.recycler_view) {
    private lateinit var binding: RecyclerViewBinding
    private val args: ForecastFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // getting the zip code from the argument in XML file.

        val zipCodeData = args.zipCodeArgument
        val latData = args.latitudeArgument
        val longData = args.longitudeArgument
        binding = RecyclerViewBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.forecasts.observe(viewLifecycleOwner) { forecasts ->
            ForecastAdapter(forecasts.list)

            binding.recyclerView.adapter = ForecastAdapter(forecasts.list)
        }

        try {
            viewModel.loadData(zipCodeData, latData.toString(), longData.toString())
        } catch (e: HttpException) {
            Log.d("API Call Error: ", e.toString())
        }


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().title = "Forecast"
    }

    override fun onResume() {
        super.onResume()
    }
}
