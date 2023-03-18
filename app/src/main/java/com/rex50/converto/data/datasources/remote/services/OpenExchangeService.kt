package com.rex50.converto.data.datasources.remote.services

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeService {

    @GET(Endpoints.LATEST_RATES)
    suspend fun fetchLatestRates(
        @Query("app_id") appId: String,
        @Query("prettyprint") prettyPrint: Boolean = false
    ): Response<JsonElement>

    @GET(Endpoints.ALL_CURRENCIES)
    suspend fun fetchCurrencies(
        @Query("app_id") appId: String,
        @Query("prettyprint") prettyPrint: Boolean = false
    ): Response<JsonElement>

}