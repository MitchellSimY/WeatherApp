package com.example.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityForecastBinding
import com.example.weatherapp.databinding.ActivityMainBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastAdapter(private var data: List<DayForecast>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateView: TextView = view.findViewById(R.id.date);
        val dayTempView: TextView = view.findViewById(R.id.tempLabel);
        val maxMinTempView: TextView = view.findViewById(R.id.highLowLabel);
        val sunriseView: TextView = view.findViewById(R.id.sunriseLabel);
        val sunsetView: TextView = view.findViewById(R.id.sunsetLabel);
        val conditionIcon: ImageView = view.findViewById(R.id.conditionIcon)


        @SuppressLint("NewApi")
        fun bind(dayForecast: DayForecast) {
            val instant = Instant.ofEpochSecond(dayForecast.dt)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("MMM dd")

            val sunriseTimeInstant = Instant.ofEpochSecond(dayForecast.sunrise)
            val sunsetTimeInstant = Instant.ofEpochSecond(dayForecast.sunset)

            val sunriseDateTime = LocalDateTime.ofInstant(sunriseTimeInstant, ZoneId.systemDefault())
            val sunsetDateTime = LocalDateTime.ofInstant(sunsetTimeInstant, ZoneId.systemDefault())

            val sunriseFormatter = DateTimeFormatter.ofPattern("h:mma")
            val sunsetFormatter = DateTimeFormatter.ofPattern("h:mma")

            val iconName = dayForecast.weather.firstOrNull()?.icon;
            val iconUrl = "https://openweathermap.org/img/wn/${iconName}@2x.png"
            Glide.with(conditionIcon)
                .load(iconUrl)
                .into(conditionIcon)

            dateView.text = formatter.format(dateTime);
            dayTempView.text = "Temp ${dayForecast.temp.day}°";
            maxMinTempView.text = "High ${dayForecast.temp.max}°  Low ${dayForecast.temp.min}°"
            sunriseView.text = "Sunrise ${sunriseFormatter.format(sunriseDateTime)}"
            sunsetView.text = "Sunset  ${sunsetFormatter.format(sunsetDateTime)}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_date, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}