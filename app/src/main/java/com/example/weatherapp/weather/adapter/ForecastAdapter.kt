package com.example.weatherapp.weather.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.weather.api.ListElement
import com.example.weatherapp.weather.api.Welcome10

class ForecastAdapter (private val forecastList: List<ListElement>?) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflating list data from list_item to view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }

    // Binding cryptocurrency list to ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecastList!![position])
    }
    override fun getItemCount() = forecastList!!.size
    // Iterating ViewHolder and loading it's
    // content to our Image and Text ViewsT
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(index: ListElement) {
            //itemView.findViewById<TextView>(R.id.tv_day).text = ""+index
            itemView.findViewById<TextView>(R.id.tv_icon).text = ""+index.weather.get(0).icon
            itemView.findViewById<TextView>(R.id.tv_temp).text = ""+index.main.temp
            //Log.d("ForecastAdapter", ""+index)
        }
    }
}
