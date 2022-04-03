package com.example.weatherapp.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ZipcodeentryBinding
import com.example.weatherapp.viewmodels.SearchViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class SearchFragment : Fragment(R.layout.zipcodeentry) {

    private lateinit var binding: ZipcodeentryBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var flp : FusedLocationProviderClient

    private var lat : Double? = null
    private var long : Double? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.zipcodeentry, container, false)
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        val locationButton = view.findViewById<Button>(R.id.findLocationButton)
        val zipCodeText = view.findViewById<EditText>(R.id.zipCode)
        var zipCode: String? = null
//        var long : Double
//        var lat : Double

        flp = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        binding = ZipcodeentryBinding.inflate(layoutInflater)
        viewModel = SearchViewModel()
        viewModel.enableButton.observe(viewLifecycleOwner) { enable ->
            submitButton.isEnabled = enable
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


        submitButton.setOnClickListener {
            val action = SearchFragmentDirections.navZipToCurrentConditions(zipCode)
            findNavController().navigate(action)
        }

        locationButton.setOnClickListener {
            requestLocationPermission()
        }

        return view
    }

    private fun requestLocationPermission() {
        val task = flp.lastLocation

        if (ActivityCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            return
        }
//        Toast.makeText(this.requireActivity(), "Here", Toast.LENGTH_SHORT).show()
        task.addOnSuccessListener {
            if (it != null) {
                lat = it.latitude
                long = it.longitude
                Toast.makeText(this.requireActivity(), "Lat: ${it.latitude}, Long: ${it.longitude}", Toast.LENGTH_SHORT).show()
            } else if (it == null) {
                Toast.makeText(this.requireActivity(), "This bitch is null", Toast.LENGTH_SHORT).show()
            }
        }
        Log.d("This Lat: ", lat.toString())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().title = "Zip Code"

        binding = ZipcodeentryBinding.inflate(layoutInflater)
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }
}























