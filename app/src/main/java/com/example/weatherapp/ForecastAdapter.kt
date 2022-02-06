package com.example.weatherapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ForecastAdapter(private var data: List<Data>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateView : TextView = view.findViewById(R.id.date);
        val tempView : TextView = view.findViewById(R.id.tempLabel);
        val highLowView : TextView = view.findViewById(R.id.highLowLabel);
        val sunriseView : TextView = view.findViewById(R.id.sunriseLabel);
        val sunsetView : TextView = view.findViewById(R.id.sunsetLabel);


        @SuppressLint("NewApi")
        fun bind(data: Data) {
            val instant = Instant.ofEpochSecond(data.date)
            val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("MMM dd")
            dateView.text = formatter.format(dateTime);
            tempView.text = "Temp ${data.temp}";
            highLowView.text = "High ${data.highTemp}  Low ${data.lowTemp}"
            sunriseView.text = "Sunrise ${data.sunriseTime}"
            sunsetView.text = "Sunset ${data.sunsetTime}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_date, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}