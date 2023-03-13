package com.rex50.converto.data.repos.open_exchange

import com.rex50.converto.data.datasources.local.prefs.OpenExchangeLocalDataSourceImpl
import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.data.models.CurrenciesRateResponse
import com.rex50.converto.utils.Result
import com.rex50.converto.utils.extensions.mapSafelyIfSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class OpenExchangeRepoImpl
@Inject
constructor(
    private val remoteDataSource: OpenExchangeRemoteDataSourceImpl,
    private val localDataSource: OpenExchangeLocalDataSourceImpl,
    private val responseMapper: CurrenciesResponseMapper
) : OpenExchangeRepo {

    companion object {
        private const val DATA_VALIDITY_HOURS = 3
    }

    override suspend fun fetchCurrencies(): Result<CurrenciesRateResponse> =
        withContext(Dispatchers.IO) {
            val lastUpdateTime = localDataSource.fetchLastUpdateTime()

            val localData = if(lastUpdateTime.plusHours(DATA_VALIDITY_HOURS).isAfterNow) {
                localDataSource.fetchCurrenciesResponse()
            } else {
                localDataSource.storeCurrenciesResponse(JSONObject())
                null
            }

            val response = if(localData != null)
                Result.Success(localData)
            else
                remoteDataSource.fetchCurrenciesRate()

            return@withContext response.mapSafelyIfSuccess {
                // Update local
                if(localData == null) {
                    localDataSource.apply {
                        storeCurrenciesResponse(it)
                        updateLastUpdateTimeToNow()
                    }
                }

                responseMapper.jsonToCurrenciesRateResponse(it)
            }
        }

}