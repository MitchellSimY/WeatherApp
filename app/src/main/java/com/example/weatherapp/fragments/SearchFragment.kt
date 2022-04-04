package com.example.weatherapp.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ZipcodeentryBinding
import com.example.weatherapp.viewmodels.SearchViewModel
import com.google.android.gms.location.*
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

        flp = LocationServices.getFusedLocationProviderClient(activity as MainActivity)

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
            val action = SearchFragmentDirections.navZipToCurrentConditions(zipCode, null, null)
            findNavController().navigate(action)
        }

        locationButton.setOnClickListener {
            daLocation()

        }

        return view
    }

    private fun daLocation() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        if (checkPermissions()) {
            if (ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestLocPermission()
                return
            }

            flp = LocationServices.getFusedLocationProviderClient(this.requireContext())
            flp.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()!!)

            flp.lastLocation.addOnSuccessListener(activity as MainActivity) { task ->
                val location : Location? = task

                if (location == null) {
                    Toast.makeText(this.requireContext(), "NULL LOCATION", Toast.LENGTH_LONG).show()

                } else {
                    long = location.longitude
                    lat = location.latitude
                    Log.d("97LatLong","\"${lat}, ${long}\"")
                    Toast.makeText(this.requireContext(), "${lat}, $long", Toast.LENGTH_LONG).show()
                    val action = SearchFragmentDirections.navZipToCurrentConditions(null,
                        lat.toString(), long.toString())
                    findNavController().navigate(action)
                }

            }
        } else {
            requestLocPermission()
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation : Location = locationResult.lastLocation
            lat = lastLocation.latitude
            long = lastLocation.longitude
            Log.d("LAT LONG BITCH",  "${lat}, ${long}")
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PARL) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.requireContext(), "GRANTED", Toast.LENGTH_LONG).show()
                daLocation()
            } else {
                Toast.makeText(this.requireContext(), "DENIED", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun requestLocPermission() {
        ActivityCompat.requestPermissions(
            activity as MainActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PARL)
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().title = "Zip Code"

        binding = ZipcodeentryBinding.inflate(layoutInflater)
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
        private const val PARL = 100
    }
}























