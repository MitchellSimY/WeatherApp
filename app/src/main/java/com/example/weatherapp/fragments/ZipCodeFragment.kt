package com.example.weatherapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ZipcodeentryBinding

class ZipCodeFragment : Fragment(R.layout.zipcodeentry) {

    private lateinit var binding : ZipcodeentryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.zipcodeentry, container, false)

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_zipCodeFragment_to_currentConditionsFragment)
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().title = "Zip Code"
        Log.d("Testing From Zip Code: ", "Before the click")

        binding = ZipcodeentryBinding.inflate(layoutInflater)




    }

}