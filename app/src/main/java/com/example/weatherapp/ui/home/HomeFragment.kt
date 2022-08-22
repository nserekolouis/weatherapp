package com.example.weatherapp.ui.home

import CryptocurrencyAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.location.LocationViewModel
import com.example.weatherapp.weather.adapter.ForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null



    //location
    private lateinit var locationViewModel: LocationViewModel

    //controls
    //private lateinit var longtitude: TextView
    //private lateinit var latitude: TextView
    //private lateinit var info: TextView

    //current weather controls
    private lateinit var tv_temp: TextView
    private lateinit var tv_clouds: TextView

    private lateinit var tv_temp_min: TextView
    private lateinit var tv_temp_current: TextView
    private lateinit var tv_temp_max: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //forecast recyler view
    private lateinit var forecastList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        //initiate location view model
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        //initiate controls
        //latitude = root.findViewById<TextView>(R.id.latitude)
        //longtitude = root.findViewById<TextView>(R.id.longitude)
        //info = root.findViewById<TextView>(R.id.info)

        //current weather controls
        tv_temp = root.findViewById<TextView>(R.id.tv_temp)
        tv_clouds = root.findViewById<TextView>(R.id.tv_clouds)
        tv_temp_min = root.findViewById<TextView>(R.id.tv_temp_min)
        tv_temp_current = root.findViewById<TextView>(R.id.tv_temp_current)
        tv_temp_max = root.findViewById<TextView>(R.id.tv_temp_max)

        //forecastlist
        forecastList = root.findViewById<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(getContext())

        observerLocationUpdates()
        observerCurrentWeather()
        observerForecastWeather()
        return root
    }

    private fun observerCurrentWeather() {
        //TODO("Not yet implemented")
        locationViewModel.currentLiveData.observe(viewLifecycleOwner, {
            Log.d("Current Weather", it.get(0)?.clouds.toString())
            tv_clouds.text = it.get(0)?.weather?.get(0)?.main
            tv_temp.text = it.get(0)?.main?.temp.toString()
            tv_temp_min.text = it.get(0)?.main?.tempMin.toString()
            tv_temp_current.text = it.get(0)?.main?.tempMin.toString()
            tv_temp_max.text = it.get(0)?.main?.tempMax.toString()
            //if (it.get(0)?.weather?.get(0)?.main == "Sun") {
                //sunny

            //} else if (it.get(0)?.weather?.get(0)?.main == "Clouds"){
                //cloudy

            //}else if (it.get(0)?.weather?.get(0)?.main == "Rain") {
                // rainny

            //}else{

            //}
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

    private fun observerLocationUpdates() {
        //TODO("Not yet implemented")
        locationViewModel.getLocationData.observe(viewLifecycleOwner, Observer {
            //longtitude.text = it.longitude.toString()
            Log.d(TAG,it.longitude.toString())
            //latitude.text = it.latitude.toString()
            Log.d(TAG,it.latitude.toString())
            //info.text = getString(R.string.location_successfully_received)
            //Log.d(TAG,it.info.toString())
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