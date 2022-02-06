package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastBtn = findViewById<Button>(R.id.forecastButton);

        forecastBtn.setOnClickListener(View.OnClickListener{
            val forecastIntent = Intent(this,ForecastActivity::class.java)
            startActivity(forecastIntent);
        })
    }
}