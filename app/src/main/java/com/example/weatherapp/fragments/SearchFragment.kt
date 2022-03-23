package com.example.weatherapp.fragments

import android.os.Bundle
import android.text.Editable
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
import com.example.weatherapp.viewmodels.SearchViewModel

class SearchFragment : Fragment(R.layout.zipcodeentry) {

    private lateinit var binding: ZipcodeentryBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.zipcodeentry, container, false)
        val button = view.findViewById<Button>(R.id.button)
        val zipCodeText = view.findViewById<EditText>(R.id.zipCode)
        var zipCode: String? = null

        binding = ZipcodeentryBinding.inflate(layoutInflater)
        viewModel = SearchViewModel()

        viewModel.enableButton.observe(viewLifecycleOwner) { enable ->
            button.isEnabled = enable
        }

        zipCodeText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val newZip = p0.toString()
                zipCode = newZip
                newZip.let { viewModel.updateZipCode(it) }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        button.setOnClickListener {
            val action = SearchFragmentDirections.navZipToCurrentConditions(zipCode)
            findNavController().navigate(action)
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().title = "Zip Code"

        binding = ZipcodeentryBinding.inflate(layoutInflater)
    }
}