package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.weatherapp.databinding.CurrentconditionsBinding
import com.example.weatherapp.databinding.ZipcodeentryBinding

class ZipCodeFragment : Fragment(R.layout.zipcodeentry) {

    private lateinit var binding : ZipcodeentryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ZipcodeentryBinding.inflate(layoutInflater)

        val submitButton : Button = binding.button
        binding.button.setOnClickListener() {
            val fragment = CurrentConditionsFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragmentContainer, fragment)?.commit()
        }


    }

}