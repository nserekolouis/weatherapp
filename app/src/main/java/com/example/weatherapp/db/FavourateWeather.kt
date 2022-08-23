package com.example.weatherapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.api.CWeather

@Entity
data class FavourateWeather(
    var cWeather: CWeather
){
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0
    @Embedded
    var favourate = cWeather
}