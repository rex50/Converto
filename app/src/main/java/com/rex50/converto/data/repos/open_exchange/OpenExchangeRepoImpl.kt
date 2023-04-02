package com.rex50.converto.data.repos.open_exchange

import com.rex50.converto.data.datasources.local.OpenExchangeLocalDataSourceImpl
import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.data.models.CountriesResponse
import com.rex50.converto.data.models.CurrenciesRateResponse
import com.rex50.converto.utils.Result
import com.rex50.converto.utils.extensions.mapSafelyIfSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
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
        private const val DATA_VALIDITY_MINUTES = 30
    }

    override suspend fun fetchCurrencies(): Result<CurrenciesRateResponse> =
        withContext(Dispatchers.IO) {
            val lastUpdateTime = localDataSource.fetchLastUpdateTime()

            val localCurrenciesRateData = getLocalCurrenciesRateOrNull(lastUpdateTime)

            val response = async {
                localCurrenciesRateData?.let { Result.Success(it) }
                    ?: remoteDataSource.fetchCurrenciesRate()
            }

            // NOTE: We should not call api here instead
            // We can move below api call to Domain (UseCase) layer
            // but as we are going to have only single
            val countriesMap = getLocalCountriesMapOrNull()

            return@withContext response.await().mapSafelyIfSuccess {
                // Update local
                val time = if (localCurrenciesRateData == null) {
                    updateLocalData(response = it)
                    DateTime.now().millis
                } else
                    lastUpdateTime.millis

                responseMapper.jsonToCurrenciesRateResponse(
                    jsonObject = it,
                    countriesMap = countriesMap,
                    lastUpdateTime = time
                )
            }
        }

    override suspend fun fetchCountries(): Result<CountriesResponse> = withContext(Dispatchers.IO) {
        val localCountriesResponse = localDataSource.fetchCountriesResponse()

        val response = localCountriesResponse?.let { Result.Success(it) }
            ?: remoteDataSource.fetchCountries()

        return@withContext response.mapSafelyIfSuccess {
            if (localCountriesResponse == null)
                localDataSource.storeCountriesResponse(it)

            CountriesResponse(responseMapper.jsonToCountriesResponse(it))
        }
    }

    private suspend fun updateLocalData(response: JSONObject) = localDataSource.apply {
        storeCurrenciesResponse(response)
        updateLastUpdateTimeToNow()
    }

    private suspend fun getLocalCountriesMapOrNull(): HashMap<String, String>? =
        withContext(Dispatchers.IO) {
            val countriesResponse = async { fetchCountries() }
            return@withContext countriesResponse.await().let {
                if (it is Result.Success)
                    it.data.countriesMap
                else
                    null
            }
        }

    private suspend fun getLocalCurrenciesRateOrNull(lastUpdateTime: DateTime): JSONObject? =
        if (lastUpdateTime.plusMinutes(DATA_VALIDITY_MINUTES).isAfterNow) {
            localDataSource.fetchCurrenciesResponse()
        } else {
            localDataSource.storeCurrenciesResponse(JSONObject())
            null
        }

}