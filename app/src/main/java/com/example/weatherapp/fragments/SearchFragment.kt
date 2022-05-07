package com.example.weatherapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
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
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ZipcodeentryBinding
import com.example.weatherapp.models.CurrentConditions
import com.example.weatherapp.services.TimerService
import com.example.weatherapp.viewmodels.CurrentConditionsViewModel
import com.example.weatherapp.viewmodels.SearchViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.zipcodeentry) {

    private lateinit var binding: ZipcodeentryBinding
    private lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var viewModelCurrentConditions: CurrentConditionsViewModel

    private lateinit var flp: FusedLocationProviderClient
    private lateinit var serviceIntent: Intent

    private var lat: Double? = null
    private var long: Double? = null
    private val CHANNELID = "channel_id_example_001"
    private val notificationId = 101
    private var timerStarted = false
    private var time = 0.0


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.zipcodeentry, container, false)
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        val locationButton = view.findViewById<Button>(R.id.findLocationButton)
        val notificationButton = view.findViewById<Button>(R.id.notificationToggle)
        val zipCodeText = view.findViewById<EditText>(R.id.zipCode)
        var zipCode: String? = null

        createNotificationChannel()
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
            daLocation(true)
        }

        notificationButton.setOnClickListener {
            notificationToggle()
        }
        serviceIntent = Intent(context, TimerService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        return view
    }

    // Timer
    private fun notificationToggle() {
        if (checkPermissions()) {
            if (lat == null || long == null) {
                daLocation(false)
            }
            startStopTimer()
        } else {
            daLocation(false)
        }
    }

    private fun resetTimer() {
        time = 0.0
        stopTimer()
        startStopTimer()
    }

    private fun startStopTimer() {
        if (timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
        val notificationButton = view?.findViewById<Button>(R.id.notificationToggle)
        if (notificationButton != null) {
            notificationButton.text = "Disable Notifications"
        }
        timerStarted = true
    }

    private fun stopTimer() {
        requireActivity().stopService(serviceIntent)
        val notificationButton = view?.findViewById<Button>(R.id.notificationToggle)
        if (notificationButton != null) {
            notificationButton.text = "Enable Notifications"
        }
        timerStarted = false
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            Log.d("Da Time: ", getTimeStringFromDouble(time))
            if (getTimeStringFromDouble(time) == "30:00") {
//          5 second timer test
//          if (getTimeStringFromDouble(time) == "00:05") {
                sendNotification()
                resetTimer()
            }
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(minutes, seconds)
    }

    private fun makeTimeString(min: Int, sec: Int): String = String.format("%02d:%02d", min, sec)

    // Notifications
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNELID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun sendNotification() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

        try {
            Log.d("Lat ", "${lat.toString()}")
            Log.d("Long ", "${long.toString()}")
            viewModelCurrentConditions.loadData(null, lat.toString(), long.toString())

        } catch (e: HttpException) {
            Log.d("API Call error: ", e.toString())
            Log.d("API message", e.message())
            Log.d("API localizeMsg", "${e.localizedMessage}")
        }

        viewModelCurrentConditions?.currentConditions.observe(viewLifecycleOwner) { currentConditions ->
            buildNotification(currentConditions, pendingIntent)
        }

    }

    private fun buildNotification(
        currentConditions: CurrentConditions,
        pendingIntent: PendingIntent
    ) {

        val builder = context?.let {
            NotificationCompat.Builder(it, CHANNELID)
                .setSmallIcon(R.drawable.smallicon)
                .setContentTitle("Weather in ${currentConditions.name}")
                .setContentText("Current Temperature is ${currentConditions.main.temp.toInt()}")
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                        "Current Temperature is ${currentConditions.main.temp.toInt()}\n" +
                                "Feels like ${currentConditions.main.feelsLike.toInt()}\n"
                    )
                )
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }

        if (builder != null) {
            with(context?.let { NotificationManagerCompat.from(it) }) {
                this?.notify(notificationId, builder.build())
            }
        }
    }

    // Location
    private fun daLocation(navigate: Boolean) {
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
                val location: Location? = task

                if (location == null) {
                    Toast.makeText(this.requireContext(), "NULL LOCATION", Toast.LENGTH_LONG).show()

                } else {
                    long = location.longitude
                    lat = location.latitude
                    Toast.makeText(this.requireContext(), "${lat}, $long", Toast.LENGTH_LONG).show()
                    if (navigate) {
                        val action = SearchFragmentDirections.navZipToCurrentConditions(
                            null,
                            lat.toString(), long.toString()
                        )
                        findNavController().navigate(action)
                    }
                }
            }
        } else {
            requestLocPermission()
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            lat = lastLocation.latitude
            long = lastLocation.longitude
        }
    }

    private fun requestLocPermission() {
        ActivityCompat.requestPermissions(
            activity as MainActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PARL
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
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
        //        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
        private const val PARL = 100
    }
}