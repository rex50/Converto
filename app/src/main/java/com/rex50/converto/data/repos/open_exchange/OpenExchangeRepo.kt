package com.rex50.converto.data.repos.open_exchange

import com.rex50.converto.data.models.CountriesResponse
import com.rex50.converto.data.models.CurrenciesRateResponse
import com.rex50.converto.utils.Result

interface OpenExchangeRepo {
    suspend fun fetchCurrencies(): Result<CurrenciesRateResponse>

    suspend fun fetchCountries(): Result<CountriesResponse>
}