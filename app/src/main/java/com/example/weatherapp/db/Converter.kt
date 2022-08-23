package com.example.weatherapp.db

import androidx.room.TypeConverter
import com.example.weatherapp.api.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun toCWeather(value:String):CWeather{
        val type=object :TypeToken<CWeather>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toCWeatherJson(value:CWeather):String{
        val type=object :TypeToken<CWeather>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toCoord(value:String):Coord{
        val type=object :TypeToken<Coord>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toCoordJson(value:Coord):String{
        val type=object :TypeToken<Coord>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toWeather(value:String): List<Weather> {
        val type=object :TypeToken<List<Weather>>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toWeatherJson(value:List<Weather>):String{
        val type=object :TypeToken<List<Weather>>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toMain(value:String): Main {
        val type=object :TypeToken<Main>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toMainJson(value:Main):String{
        val type=object :TypeToken<Main>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toWind(value:String): Wind {
        val type=object :TypeToken<Wind>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toWindJson(value:Wind):String{
        val type=object :TypeToken<Wind>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toRain(value:String): Rain? {
        val type=object :TypeToken<Rain?>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toRainJson(value:Rain?):String?{
        val type=object :TypeToken<Rain?>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toClouds(value:String): Clouds {
        val type=object :TypeToken<Clouds>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toCloudsJson(value:Clouds):String{
        val type=object :TypeToken<Clouds>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toSys(value:String):Sys{
        val type=object :TypeToken<Sys>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toSysJson(value:Sys):String{
        val type=object :TypeToken<Sys>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toFWeather(value:String):FWeather{
        val type=object :TypeToken<FWeather>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toFWeatherJson(value:FWeather):String{
        val type=object :TypeToken<FWeather>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toListElement(value:String):List<ListElement>{
        val type = object :TypeToken<List<ListElement>>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toListElementJson(value:List<ListElement>):String{
        val type=object :TypeToken<List<ListElement>>(){}.type
        return Gson().toJson(value,type)
    }

    @TypeConverter
    fun toCity(value:String):City{
        val type = object :TypeToken<City>(){}.type
        return Gson().fromJson(value,type)
    }

    @TypeConverter
    fun toCityJson(value:City):String{
        val type=object :TypeToken<City>(){}.type
        return Gson().toJson(value,type)
    }


}