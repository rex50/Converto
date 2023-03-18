package com.rex50.converto.ui.models

data class Currency(
    val currency: String = "USD",
    val rate: Double = 1.0,
    val country: String = "",
    var convertedCurrency: Double? = null
)
