package com.example.proyectodivisas.model

data class ExchangeRatesResponse(
    val result: String,
    val documentation: String,
    val terms_of_use: String,
    val time_last_update_unix: Long,
    val time_next_update_unix: Long,
    val base_code: String,
    val conversion_rates: Map<String, Double>
)