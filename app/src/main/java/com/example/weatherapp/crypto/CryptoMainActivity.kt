package com.example.weatherapp.crypto

import CryptocurrencyAdapter
import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.location.ForegroundOnlyLocationService
import com.example.weatherapp.location.SharedPreferenceUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import android.provider.Settings
import com.example.weatherapp.location.toText



@AndroidEntryPoint
class CryptoMainActivity : AppCompatActivity(){
    private lateinit var cryptocurrencyList: RecyclerView
    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crypto_activity_main)

        cryptocurrencyList = findViewById<RecyclerView>(R.id.cryptocurrency_list)
        cryptocurrencyList.layoutManager = LinearLayoutManager(this)

        observeCryptoCurrency()


    }

    private fun observeCryptoCurrency() {
        viewModel.cryptoCurrency.observe(this) {
            cryptocurrencyList.adapter = CryptocurrencyAdapter(it)
        }
    }


}