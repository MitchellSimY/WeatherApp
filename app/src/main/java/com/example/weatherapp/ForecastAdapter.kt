package com.example.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.RowDateBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastAdapter(private var data: List<DayForecast>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(private var binding: RowDateBinding) : RecyclerView.ViewHolder(binding.root) {

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

            Glide.with(binding.conditionIcon)
                .load(iconUrl)
                .into(binding.conditionIcon)

            binding.date.text = formatter.format(dateTime);
            binding.tempLabel.text = "Temp ${dayForecast.temp.day}°";
            binding.highLowLabel.text = "High ${dayForecast.temp.max}°  Low ${dayForecast.temp.min}°"
            binding.sunriseLabel.text = "Sunrise ${sunriseFormatter.format(sunriseDateTime)}"
            binding.sunsetLabel.text = "Sunset  ${sunsetFormatter.format(sunsetDateTime)}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowDateBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}