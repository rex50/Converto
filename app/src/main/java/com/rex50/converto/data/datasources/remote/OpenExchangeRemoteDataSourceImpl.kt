package com.rex50.converto.data.datasources.remote

import com.rex50.converto.BuildConfig
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import com.rex50.converto.utils.Result
import com.rex50.converto.utils.extensions.mapSafelyToResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class OpenExchangeRemoteDataSourceImpl
@Inject
constructor(
    private val openExchangeService: OpenExchangeService
) {

    private val key = BuildConfig.OPEN_EXCHANGE_SERVICE_KEY

    suspend fun fetchCurrenciesRate(): Result<JSONObject> = withContext(Dispatchers.IO) {
        val response = openExchangeService.fetchCurrencies(key)
        return@withContext response.mapSafelyToResult {
            JSONObject(it.toString())
        }
    }

}