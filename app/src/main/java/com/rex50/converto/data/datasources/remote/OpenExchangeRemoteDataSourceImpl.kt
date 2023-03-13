package com.rex50.converto.data.datasources.remote

import com.rex50.converto.data.datasources.local.DEMO_RESPONSE
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import javax.inject.Inject
import com.rex50.converto.utils.Result
import com.rex50.converto.utils.extensions.mapSafelyToResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

class OpenExchangeRemoteDataSourceImpl
@Inject
constructor(
    private val openExchangeService: OpenExchangeService
) {

    suspend fun fetchCurrenciesRate(): Result<JSONObject> = withContext(Dispatchers.IO) {
        val response = openExchangeService.fetchCurrencies("5d1100fd57f242b99c67a82dff45f33c")
        return@withContext response.mapSafelyToResult {
            JSONObject(it.toString())
        }
    }

}