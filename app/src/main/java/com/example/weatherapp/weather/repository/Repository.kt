package com.example.weatherapp.weather.repository

import com.example.weatherapp.weather.api.ApiService
import com.example.weatherapp.weather.api.Welcome10
import com.example.weatherapp.weather.api.Welcome3
import retrofit2.Response
import retrofit2.http.Path

class Repository(private val apiService:ApiService){

    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        appid: String
    ): Response<Welcome3> {
        return apiService.getCurrentWeather(lat,lon,appid)
    }

    suspend fun getFiveDayForecast(
        lat: String,
        lon: String,
        appid: String,
        cnt: Int
    ): Response<Welcome10> {
        return apiService.getFiveDayForecast(lat,lon,appid,cnt)
    }
}