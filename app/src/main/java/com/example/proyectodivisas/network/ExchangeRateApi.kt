package com.example.proyectodivisas.network

import com.example.proyectodivisas.model.ExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("dea02f05b88bd35f45e80a89/latest/USD")
    suspend fun getLatestRates(): ExchangeRatesResponse
}