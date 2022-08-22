package com.example.weatherapp.crypto

interface CryptocurrencyRepository {
    fun getCryptoCurrency(): List<Cryptocurrency>
}