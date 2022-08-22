package com.example.weatherapp.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.androidisland.ezpermission.EzPermission
import com.example.weatherapp.R
import com.example.weatherapp.crypto.MainViewModel
import com.example.weatherapp.weather.repository.Repository
import com.example.weatherapp.weather.service.MyLocationService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by Waheed on 03,December,2019
 */

/**
 * Constants Values
 */
const val LOCATION_PERMISSION_REQUEST = 101


/**
 * Main Activity
 */

@AndroidEntryPoint
class LocationActivity: AppCompatActivity() {

    private lateinit var locationViewModel: LocationViewModel
    private var isGPSEnabled = false

    //controls
    private lateinit var longtitude: TextView
    private lateinit var latitude: TextView
    private lateinit var info: TextView




    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    /**
     * onCreate of activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_activity)

        // Instance of LocationViewModel
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        //controls
        latitude = findViewById<TextView>(R.id.latitude)
        longtitude = findViewById<TextView>(R.id.longitude)
        info = findViewById<TextView>(R.id.info)



        //Check weather Location/GPS is ON or OFF
        LocationUtil(this).turnGPSOn(object :
            LocationUtil.OnLocationOnListener {
            override fun locationStatus(isLocationOn: Boolean) {
                this@LocationActivity.isGPSEnabled = isLocationOn
            }
        })

        observerCurrentWeather();
    }

    private fun observerCurrentWeather() {
        //TODO("Not yet implemented")
        //locationViewModel.countryLiveData.observe(this,{
          //  Log.d("Current Weather",it.get(0)?.clouds.toString())
        //})
    }

    /**
     * Observe LocationViewModel LiveData to get updated location
     */
    private fun observeLocationUpdates() {
        locationViewModel.getLocationData.observe(this, Observer {
            longtitude.text = it.longitude.toString()
            latitude.text = it.latitude.toString()
            info.text = getString(R.string.location_successfully_received)
            locationViewModel.loadCurrentWeather(
                it.latitude.toString(),
                it.longitude.toString(),
                "d2e5a4b57103ae30a8814d474eb2db0d")
        })
    }


    /**
     * onStart lifecycle of activity
     */
    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }


    /**
     * Initiate Location updated by checking Location/GPS settings is ON or OFF
     * Requesting permissions to read location.
     */
    private fun startLocationUpdates() {
        when {
            !isGPSEnabled -> {
                info.text = getString(R.string.enable_gps)
            }

            isLocationPermissionsGranted() -> {
                observeLocationUpdates()
            }
            else -> {
                askLocationPermission()
            }
        }
    }

    /**
     * Check the availability of location permissions
     */
    private fun isLocationPermissionsGranted(): Boolean {
        return (EzPermission.isGranted(this, locationPermissions[0])
                && EzPermission.isGranted(this, locationPermissions[1]))
    }

    /**
     *
     */
    private fun askLocationPermission() {
        EzPermission
            .with(this)
            .permissions(locationPermissions[0], locationPermissions[1])
            .request { granted, denied, permanentlyDenied ->
                if (granted.contains(locationPermissions[0]) &&
                    granted.contains(locationPermissions[1])
                ) { // Granted
                    startLocationUpdates()

                } else if (denied.contains(locationPermissions[0]) ||
                    denied.contains(locationPermissions[1])
                ) { // Denied

                    showDeniedDialog()

                } else if (permanentlyDenied.contains(locationPermissions[0]) ||
                    permanentlyDenied.contains(locationPermissions[1])
                ) { // Permanently denied
                    showPermanentlyDeniedDialog()
                }

            }
    }

    /**
     *
     */
    private fun showPermanentlyDeniedDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.title_permission_permanently_denied))
        dialog.setMessage(getString(R.string.message_permission_permanently_denied))
        dialog.setNegativeButton(getString(R.string.not_now)) { _, _ -> }
        dialog.setPositiveButton(getString(R.string.settings)) { _, _ ->
            startActivity(
                EzPermission.appDetailSettingsIntent(
                    this
                )
            )
        }
        dialog.setOnCancelListener { } //important
        dialog.show()
    }


    /**
     *
     */
    private fun showDeniedDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.title_permission_denied))
        dialog.setMessage(getString(R.string.message_permission_denied))
        dialog.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        dialog.setPositiveButton(getString(R.string.allow)) { _, _ ->
            askLocationPermission()
        }
        dialog.setOnCancelListener { } //important
        dialog.show()
    }

    /**
     * On Activity Result for locations permissions updates
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOCATION_PERMISSION_REQUEST) {
                isGPSEnabled = true
                startLocationUpdates()
            }
        }
    }

}