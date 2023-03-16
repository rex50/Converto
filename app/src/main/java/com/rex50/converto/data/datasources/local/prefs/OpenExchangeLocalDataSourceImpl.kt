package com.rex50.converto.data.datasources.local.prefs

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class OpenExchangeLocalDataSourceImpl
@Inject
constructor(context: Context) : Prefs(context) {

    suspend fun fetchCurrenciesResponse(): JSONObject? = withContext(Dispatchers.IO) {
        return@withContext try {
            SharedPrefsKeys.CURRENCIES_RESPONSE.getString()
                .takeIf { it.isNotBlank() && it != "{}" }
                ?.let { JSONObject(it) }
        } catch (e: JSONException) {
            null
        }
    }

    suspend fun fetchLastUpdateTime(): DateTime = withContext(Dispatchers.IO) {
        return@withContext DateTime(SharedPrefsKeys.LAST_UPDATED_TIME.getLong())
    }

    suspend fun storeCurrenciesResponse(jsonObject: JSONObject) = withContext(Dispatchers.IO) {
        SharedPrefsKeys.CURRENCIES_RESPONSE.put(jsonObject.toString())
    }

    suspend fun updateLastUpdateTimeToNow() = withContext(Dispatchers.IO) {
        SharedPrefsKeys.LAST_UPDATED_TIME.put(DateTime.now().millis)
    }

}