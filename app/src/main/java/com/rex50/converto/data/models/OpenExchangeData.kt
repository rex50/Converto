package com.rex50.converto.data.models

import kotlinx.serialization.Serializable

@Serializable
data class OpenExchangeData(
    val currenciesResponse: String? = null,
    val countriesResponse: String? = null,
    val lastUpdateTime: Long? = null
)
