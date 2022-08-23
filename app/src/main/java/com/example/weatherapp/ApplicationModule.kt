package com.example.weatherapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.weatherapp.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApp(): Application {
        return WeatherApplication.instance
    }

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext applicationContext: Context
    ) =  Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "weather-appdb"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCurrentWeatherDao(db: AppDatabase) = db.currentWeatherDao()

    @Singleton
    @Provides
    fun provideForecastWeatherDao(db: AppDatabase) = db.forecastWeatherDao()
}