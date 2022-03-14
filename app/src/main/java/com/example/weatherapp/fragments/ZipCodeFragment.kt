package com.example.weatherapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ZipcodeentryBinding
import com.example.weatherapp.viewmodels.ZipCodeViewModel

class ZipCodeFragment : Fragment(R.layout.zipcodeentry) {

    private lateinit var binding : ZipcodeentryBinding
    private lateinit var viewModel: ZipCodeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.zipcodeentry, container, false)
        val button = view.findViewById<Button>(R.id.button)
        val zipCode = view.findViewById<EditText>(R.id.zipCode)

        binding = ZipcodeentryBinding.inflate(layoutInflater)
        viewModel = ZipCodeViewModel()

        viewModel.enableButton.observe(viewLifecycleOwner) {
            enable -> button.isEnabled = enable
        }

        zipCode.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val newZip = p0.toString()
                newZip.let {viewModel.updateZipCode(it)}
            }

            override fun afterTextChanged(p0: Editable?) {
            }


        })


        // When button is clicked, go here
//        view.button.setOnClickListener {
//            findNavController().navigate(R.id.navZipToCurrentConditions)
//        }


        button.setOnClickListener {
            findNavController().navigate(R.id.navZipToCurrentConditions)
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