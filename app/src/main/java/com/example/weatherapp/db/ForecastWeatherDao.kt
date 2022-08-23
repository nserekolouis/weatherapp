package com.example.weatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ForecastWeatherDao {
    @Insert
    fun insertAll(forecastWeather: ForecastWeather)

    @Query("SELECT * FROM forecastWeather")
    fun getAll(): List<ForecastWeather>

    @Query("SELECT * FROM forecastweather ORDER BY uId DESC LIMIT 1")
    fun getMostRecent(): ForecastWeather
}