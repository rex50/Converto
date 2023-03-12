package com.rex50.converto.data.repos.open_exchange

import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.data.models.CurrenciesRateResponse
import com.rex50.converto.utils.Result
import com.rex50.converto.utils.extensions.mapSafelyIfSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OpenExchangeRepoImpl
@Inject
constructor(
    private val remoteDataSource: OpenExchangeRemoteDataSourceImpl,
    private val responseMapper: CurrenciesResponseMapper
) : OpenExchangeRepo {

    override suspend fun fetchCurrencies(): Result<CurrenciesRateResponse> =
        withContext(Dispatchers.IO) {
            val response = remoteDataSource.fetchCurrenciesRate()
            return@withContext response.mapSafelyIfSuccess {
                responseMapper.jsonToCurrenciesRateResponse(it)
            }
        }

}