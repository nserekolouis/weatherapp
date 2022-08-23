package com.example.weatherapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.api.ListElement
import java.time.LocalDate

class ForecastAdapter (
    private val forecastList: List<ListElement>?
    ) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        /*Inflating list data from list_item to view*/
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }


    /*Binding cryptocurrency list to ViewHolder*/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecastList!![position])
        val today = LocalDate.now()
        holder.itemView.findViewById<TextView>(R.id.tv_day).text = today.plusDays((position+1).toLong()).getDayOfWeek().name
    }
    override fun getItemCount() = forecastList!!.size

    /* Iterating ViewHolder and loading it's
    * content to our Image and Text Views
    * */
    class ViewHolder(itemView: View,) : RecyclerView.ViewHolder(itemView) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(index: ListElement) {
            itemView.findViewById<ImageView>(R.id.img_icon).setImageResource(getIcon(index.weather.get(0).icon))
            itemView.findViewById<TextView>(R.id.tv_temp).text = ""+index.main.temp

        }

        private fun getIcon(iconId: String): Int{
            val secondChar: Char = iconId[1]
            when (secondChar.toInt()) {
                1 -> {
                    return R.drawable.clear
                }
                2 -> {
                    return R.drawable.partlysunny
                }
                3 -> {
                    return R.drawable.partlysunny
                }
                4 -> {
                    return R.drawable.partlysunny
                }
                else -> {
                    return R.drawable.rain
                }
            }
        }
    }
}
