package com.example.weatherapp.ui.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.location.LocationViewModel
import com.example.weatherapp.adapter.ForecastAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    /* LocationViewModel */
    private lateinit var locationViewModel: LocationViewModel

    /* Fragment binding */
    private var _binding: FragmentHomeBinding? = null

    /* Weather controls*/
    private lateinit var tv_date: TextView
    private lateinit var tv_location: TextView
    private lateinit var tv_temp: TextView
    private lateinit var tv_clouds: TextView
    private lateinit var img_background: ImageView

    private lateinit var tv_temp_min: TextView
    private lateinit var tv_temp_current: TextView
    private lateinit var tv_temp_max: TextView

    /* This property is only valid between onCreateView and onDestroyView*/
    private val binding get() = _binding!!

    /*forecast recyler view*/
    private lateinit var forecastList: RecyclerView

    /*Inflator for theme change*/
    private lateinit var inflater: LayoutInflater

    /*Fab*/
    private lateinit var fab: FloatingActionButton

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        //initiate location view model
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)


        //current weather controls
        tv_date = root.findViewById<TextView>(R.id.tv_date)
        tv_location = root.findViewById<TextView>(R.id.tv_location)
        tv_temp = root.findViewById<TextView>(R.id.tv_temp)
        tv_clouds = root.findViewById<TextView>(R.id.tv_clouds)
        img_background = root.findViewById(R.id.img_weather)
        tv_temp_min = root.findViewById<TextView>(R.id.tv_temp_min)
        tv_temp_current = root.findViewById<TextView>(R.id.tv_temp_current)
        tv_temp_max = root.findViewById<TextView>(R.id.tv_temp_max)

        //forecastlist
        forecastList = root.findViewById<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(getContext())

        //fab
        fab = root.findViewById<FloatingActionButton>(R.id.fab);
        fab.visibility = View.GONE
        fab.setOnClickListener{
            Snackbar.make(it, "Save Favourate Location", Snackbar.LENGTH_LONG)
            //                .setAction("Action", null).show()
        }

        observerLocationUpdates()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            observerCurrentWeather()
        }
        observerForecastWeather()
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observerCurrentWeather() {
        //TODO("Not yet implemented")
        locationViewModel.currentLiveData.observe(viewLifecycleOwner, {
            /*set date time*/
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val formatted = current.format(formatter)
            tv_date.text = formatted
            tv_clouds.text = it.get(0)?.weather?.get(0)?.main
            tv_temp.text = it.get(0)?.main?.temp.toString()
            tv_temp_min.text = it.get(0)?.main?.tempMin.toString()
            tv_temp_current.text = it.get(0)?.main?.temp.toString()
            tv_temp_max.text = it.get(0)?.main?.tempMax.toString()

            /* we can now save favourate weather locations */
            fab.visibility = View.VISIBLE

            if (it.get(0)?.weather?.get(0)?.main === "Sun") {
                //sunny
                img_background.setImageResource(R.drawable.forest_sunny)
                val contextThemeWrapper: Context = ContextThemeWrapper(requireContext(), R.style.Theme_WeatherApp_Cloudy)
                this.inflater.cloneInContext(contextThemeWrapper)
                var color = resources.getColor(R.color.sunny)
                binding.root.setBackgroundColor(color)

            } else if (it.get(0)?.weather?.get(0)?.main === "Clouds"){
                //cloudy
                img_background.setImageResource(R.drawable.forest_cloudy)
                val contextThemeWrapper: Context = ContextThemeWrapper(requireContext(), R.style.Theme_WeatherApp_Sunny)
                this.inflater.cloneInContext(contextThemeWrapper)
                var color = resources.getColor(R.color.cloudy)
                binding.root.setBackgroundColor(color)

            }else{
                 //rainny
                img_background.setImageResource(R.drawable.forest_rainy)
                val contextThemeWrapper: Context = ContextThemeWrapper(requireContext(), R.style.Theme_WeatherApp_Rain)
                this.inflater.cloneInContext(contextThemeWrapper)
                var color = resources.getColor(R.color.rainny)
                binding.root.setBackgroundColor(color)
            }
        })
    }

    private fun observerForecastWeather() {
        //TODO("Not yet implemented")
        locationViewModel.forecastLiveData.observe(viewLifecycleOwner, {
            Log.d("Results Forecast", ""+it.get(0)?.list?.get(0)?.weather?.get(0)?.icon)
            Log.d("Results Forecast", ""+it.get(0)?.list?.get(0)?.weather?.get(0)?.description)
            forecastList.adapter = ForecastAdapter(it.get(0)?.list)
            Log.d("Forecast Size",""+it.get(0)?.list?.get(0)?.weather?.get(0)?.icon)
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observerLocationUpdates() {
        //TODO("Not yet implemented")
        locationViewModel.getLocationData.observe(viewLifecycleOwner, Observer {
            locationViewModel.loadCurrentWeather(
                it.latitude.toString(),
                it.longitude.toString(),
                "d2e5a4b57103ae30a8814d474eb2db0d")

            locationViewModel.loadFiveDayForecast(
                it.latitude.toString(),
                it.longitude.toString(),
                "d2e5a4b57103ae30a8814d474eb2db0d",5)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}