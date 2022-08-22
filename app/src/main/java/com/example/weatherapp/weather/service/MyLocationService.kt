package com.example.weatherapp.weather.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.R
import com.example.weatherapp.location.LocationViewModel

class MyLocationService : Service(), ViewModelStoreOwner, LifecycleOwner {

    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate() {
        super.onCreate()
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        locationViewModel.getLocationData.observe(this, Observer {
            //longtitude.text = it.longitude.toString()
            //latitude.text = it.latitude.toString()
            //info.text = getString(R.string.location_successfully_received)
            Log.d("lat",it.longitude.toString())
            Log.d("lon",it.latitude.toString())

        })
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun getViewModelStore(): ViewModelStore {
        TODO("Not yet implemented")
    }

    override fun getLifecycle(): Lifecycle {
        TODO("Not yet implemented")
    }

}