package com.rex50.converto.data.models

data class CurrenciesRateResponse(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: HashMap<String, Rate>,
    val timestamp: Int,
    val lastUpdateTime: Long
)

data class Rate(
    val rate: Double,
    val currencyCode: String,
    val country: String
)