package com.rex50.converto.data.models

data class CurrenciesRateResponse(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: HashMap<String, Double>,
    val timestamp: Int
)