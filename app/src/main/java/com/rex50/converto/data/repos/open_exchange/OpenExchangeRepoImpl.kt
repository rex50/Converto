package com.rex50.converto.data.repos.open_exchange

import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import com.rex50.converto.data.models.CurrenciesRateResponse
import org.json.JSONObject
import javax.inject.Inject

class OpenExchangeRepoImpl
@Inject
constructor(
    private val openExchangeService: OpenExchangeService
) : OpenExchangeRepo {

    override suspend fun fetchCurrencies(): CurrenciesRateResponse {
        val response = JSONObject(openExchangeService.fetchCurrencies("5d1100fd57f242b99c67a82dff45f33c").toString())
        return CurrenciesResponseMapper.jsonToCurrenciesRateResponse(response)
    }

}