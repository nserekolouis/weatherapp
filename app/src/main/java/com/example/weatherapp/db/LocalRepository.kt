package com.example.weatherapp.db

import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val currentWeatherDao: CurrentWeatherDao,
    private val forecastWeatherDao: ForecastWeatherDao
){
    suspend fun getCurrentFavourate() = currentWeatherDao.getAll()

    /* current Weather */
    suspend fun saveCurrentWeather(currentWeather: CurrentWeather) =
        currentWeatherDao.insertAll(currentWeather)

    suspend fun getMostRecentCurrentWeather() =
        currentWeatherDao.getMostRecent()

    /* forecast weather */
    suspend fun saveForecastWeather(forecastWeather: ForecastWeather) =
        forecastWeatherDao.insertAll(forecastWeather)

    suspend fun getMostRecentForecastWeather() =
        forecastWeatherDao.getMostRecent()
}