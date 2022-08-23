package com.example.weatherapp.repository

import com.example.weatherapp.api.*
import retrofit2.Response

class RemoteRepository(private val apiService:ApiService){

    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        appid: String
    ): Response<CWeather> {
        return apiService.getCurrentWeather(lat,lon,appid)
    }

    suspend fun getFiveDayForecast(
        lat: String,
        lon: String,
        appid: String,
        cnt: Int
    ): Response<FWeather> {
        return apiService.getFiveDayForecast(lat,lon,appid,cnt)
    }
}