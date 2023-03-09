package com.rex50.converto.data.repos.open_exchange

import com.rex50.converto.data.models.CurrenciesRateResponse

interface OpenExchangeRepo {
    suspend fun fetchCurrencies(): CurrenciesRateResponse
}