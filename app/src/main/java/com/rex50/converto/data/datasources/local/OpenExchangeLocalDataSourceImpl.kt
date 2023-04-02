package com.rex50.converto.data.datasources.local

import androidx.datastore.core.DataStore
import com.rex50.converto.data.models.OpenExchangeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class OpenExchangeLocalDataSourceImpl
@Inject
constructor(private val dataStore: DataStore<OpenExchangeData>) {

    suspend fun fetchCurrenciesResponse(): JSONObject? = withContext(Dispatchers.IO) {
        return@withContext try {
            dataStore.data.first().currenciesResponse
                ?.takeIf { it.isNotBlank() && it != "{}" }
                ?.let { JSONObject(it) }
        } catch (e: JSONException) {
            null
        }
    }

    suspend fun fetchCountriesResponse(): JSONObject? = withContext(Dispatchers.IO) {
        return@withContext try {
            dataStore.data.first().countriesResponse
                ?.takeIf { it.isNotBlank() && it != "{}" }
                ?.let { JSONObject(it) }
        } catch (e: JSONException) {
            null
        }
    }

    suspend fun fetchLastUpdateTime(): DateTime = withContext(Dispatchers.IO) {
        return@withContext DateTime(dataStore.data.first().lastUpdateTime)
    }

    suspend fun storeCurrenciesResponse(jsonObject: JSONObject) = withContext(Dispatchers.IO) {
        dataStore.updateData {
            it.copy(currenciesResponse = jsonObject.toString())
        }
    }

    suspend fun storeCountriesResponse(jsonObject: JSONObject) = withContext(Dispatchers.IO) {
        dataStore.updateData {
            it.copy(countriesResponse = jsonObject.toString())
        }
    }

    suspend fun updateLastUpdateTimeToNow() = withContext(Dispatchers.IO) {
        dataStore.updateData {
            it.copy(lastUpdateTime = DateTime.now().millis)
        }
    }

}