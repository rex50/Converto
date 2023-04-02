package com.rex50.converto.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLastSessionData(
    val fromCurrencyCode: String = "JPY",
    val toCurrencyCode: String = "INR"
)
