package com.example.weatherapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.api.FWeather


@Entity
data class ForecastWeather(
    var fWeather: FWeather
){
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
    @Embedded
    var forest = fWeather
}