package com.example.weatherapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.api.*

@Entity
data class CurrentWeather(
    var cWeather: CWeather
){
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
    @Embedded var current = cWeather
}


