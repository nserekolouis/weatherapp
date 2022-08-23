package com.example.weatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CurrentWeatherDao {
    @Insert
    fun insertAll(currentWeather: CurrentWeather)

    @Query("SELECT * FROM currentWeather")
    fun getAll(): List<CurrentWeather>

    @Query("SELECT * FROM currentWeather ORDER BY uId DESC LIMIT 1")
    fun getMostRecent(): CurrentWeather
}