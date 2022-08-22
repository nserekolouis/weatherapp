package com.example.weatherapp.location

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.weather.api.Welcome10
import com.example.weatherapp.weather.api.Welcome3
import com.example.weatherapp.weather.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Waheed on 03,December,2019
 */

private const val TAG = "LocationViewModel"

@HiltViewModel
class LocationViewModel
@Inject constructor(private val repository: Repository,@ApplicationContext context: Context)
    : ViewModel() {

    /**
     * MutableLiveData private field to get/save location updated values
     */
    private val locationData =
        LocationLiveData(context)

    private val currentMutableLiveData = MutableLiveData<List<Welcome3?>>()
    val currentLiveData: LiveData<List<Welcome3?>> = currentMutableLiveData

    private val forecastMutableLiveData = MutableLiveData<List<Welcome10?>>()
    val forecastLiveData: LiveData<List<Welcome10?>> = forecastMutableLiveData

    /**
     * LiveData a public field to observe the changes of location
     */
    val getLocationData: LiveData<Location> = locationData

    fun loadCurrentWeather(lat:String,lon: String, appid: String) {
        viewModelScope.launch {
            val currentWeather = repository.getCurrentWeather(lat,lon,appid)
            when (currentWeather.isSuccessful) {
                true -> {
                    with(currentWeather.body()) {
                        var currentList = listOf<Welcome3?>()
                            currentList = (currentList + currentWeather.body()?.let {
                                Welcome3(
                                    it.coord,
                                    it.weather,
                                    it.base,
                                    it.main,
                                    it.visibility,
                                    it.wind,
                                    it.rain,
                                    it.clouds,
                                    it.dt,
                                    it.sys,
                                    it.timezone,
                                    it.id,
                                    it.name,
                                    it.cod,
                                )
                            })
                        currentMutableLiveData.postValue(currentList)
                    }
                }
                else -> {
                    Timber.e(currentWeather.message())
                }
            }
        }
    }

    fun loadFiveDayForecast(lat:String,lon: String, appid: String, cnt: Int) {
        viewModelScope.launch {
            val forecast = repository.getFiveDayForecast(lat,lon,appid,cnt)
            Log.d("Results",""+forecast.body())
            when (forecast.isSuccessful) {
                true -> {
                    with(forecast.body()) {
                        var forecastList = listOf<Welcome10?>()
                        forecastList = (forecastList + forecast.body()?.let {
                            Welcome10(
                                it.cod,
                                it.message,
                                it.cnt,
                                it.list,
                                it.city
                                )
                        })
                        forecastMutableLiveData.postValue(forecastList)
                    }
                }
                else -> {
                    Timber.e(forecast.message())
                }
            }
        }
    }
}