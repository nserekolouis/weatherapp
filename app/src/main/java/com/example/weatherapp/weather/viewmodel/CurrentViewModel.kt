package com.example.weatherapp.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.hiltretrofit.main.datamodel.Country
import com.example.weatherapp.weather.repository.Repository
import javax.inject.Inject

class CurrentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val currentLiveData = MutableLiveData<List<String>?>()

    fun getCurrentWeather() = currentLiveData
}