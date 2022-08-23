package com.example.weatherapp.location

import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.NetworkUtil
import com.example.weatherapp.db.CurrentWeather
import com.example.weatherapp.db.ForecastWeather
import com.example.weatherapp.db.LocalRepository
import com.example.weatherapp.api.FWeather
import com.example.weatherapp.api.CWeather
import com.example.weatherapp.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject


private const val TAG = "LocationViewModel"

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val networkUtil: NetworkUtil,
    @ApplicationContext context: Context
) : ViewModel() {

    /**
     * MutableLiveData private field to get/save location updated values
     */

    private val locationData =
        LocationLiveData(context)

    private val currentMutableLiveData = MutableLiveData<List<CWeather?>>()
    val currentLiveData: LiveData<List<CWeather?>> = currentMutableLiveData

    private val forecastMutableLiveData = MutableLiveData<List<FWeather?>>()
    val forecastLiveData: LiveData<List<FWeather?>> = forecastMutableLiveData

    /**
     * LiveData a public field to observe the changes of location
     */
    val getLocationData: LiveData<Location> = locationData

    @RequiresApi(Build.VERSION_CODES.M)
    fun loadCurrentWeather(lat:String, lon: String, appid: String) {
        viewModelScope.launch {
            if(networkUtil.isOnline()){
                pickFromRemoteCurrent(lat,lon,appid);
            }else{
                pickFromLocalCurrent();
            }

        }
    }

    private suspend fun pickFromLocalCurrent() {
        //TODO("Not yet implemented")
        withContext(Dispatchers.IO){
            val currentWeather = localRepository.getMostRecentCurrentWeather()
            var currentList = listOf<CWeather?>()
            currentList = (currentList + currentWeather.cWeather?.let {
                currentWeather.cWeather
            })
            currentMutableLiveData.postValue(currentList)
        }
    }

    private suspend fun pickFromRemoteCurrent(lat: String, lon: String, appid: String) {
        //TODO("Not yet implemented")
        val currentWeather = remoteRepository.getCurrentWeather(lat,lon,appid)
        when (currentWeather.isSuccessful) {
            true -> {
                with(currentWeather.body()) {
                    var currentList = listOf<CWeather?>()
                    currentList = (currentList + currentWeather.body()?.let {
                        CWeather(
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
                    saveCurrentWeather(currentWeather)
                }
            }
            else -> {
                Timber.e(currentWeather.message())
            }
        }
    }

    private suspend fun saveCurrentWeather(currentWeather: Response<CWeather>) {
        //TODO("Not yet implemented")
        withContext(Dispatchers.IO){
            currentWeather.body()?.let {
                localRepository.saveCurrentWeather(CurrentWeather(currentWeather.body()!!))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun loadFiveDayForecast(lat:String, lon: String, appid: String, cnt: Int) {
        viewModelScope.launch {
            if(networkUtil.isOnline()){
                pickForecastFromRemote(lat,lon,appid,cnt)
            }else{
                pickForecastFromLocalSource();
            }
        }
    }

    private suspend fun pickForecastFromLocalSource() {
        //TODO("Not yet implemented")
        withContext(Dispatchers.IO){
            val forecast = localRepository.getMostRecentForecastWeather()
            var forecastList = listOf<FWeather?>()
            forecastList = (forecastList + forecast.fWeather?.let {
                forecast.fWeather
            })
            forecastMutableLiveData.postValue(forecastList)
        }
    }

    private suspend fun pickForecastFromRemote(lat: String, lon: String, appid: String, cnt: Int) {
        //TODO("Not yet implemented")
        val forecast = remoteRepository.getFiveDayForecast(lat,lon,appid,cnt)
        Log.d("Results",""+forecast.body())
        when (forecast.isSuccessful) {
            true -> {
                with(forecast.body()) {
                    var forecastList = listOf<FWeather?>()
                    forecastList = (forecastList + forecast.body()?.let {
                        FWeather(
                            it.cod,
                            it.message,
                            it.cnt,
                            it.list,
                            it.city
                        )
                    })
                    forecastMutableLiveData.postValue(forecastList)
                    saveForeCastWeather(forecast)
                }
            }
            else -> {
                Timber.e(forecast.message())
            }
        }
    }

    private suspend fun saveForeCastWeather(forecast: Response<FWeather>) {
        //TODO("Not yet implemented")
        withContext(Dispatchers.IO){
            forecast.body()?.let {
                localRepository.saveForecastWeather(ForecastWeather(forecast.body()!!))
            }
        }
    }
}