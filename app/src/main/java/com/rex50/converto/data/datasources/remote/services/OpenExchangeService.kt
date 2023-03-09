package com.rex50.converto.data.datasources.remote.services

import com.google.gson.JsonElement
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeService {

    @GET("latest.json")
    suspend fun fetchCurrencies(
        @Query("app_id") appId: String
    ): JsonElement

}