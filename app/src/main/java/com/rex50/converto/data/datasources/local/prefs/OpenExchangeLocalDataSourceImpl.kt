package com.rex50.converto.data.datasources.local.prefs

import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.json.JSONException
import org.json.JSONObject

class OpenExchangeLocalDataSourceImpl {

    suspend fun fetchCurrenciesResponse(): JSONObject? = withContext(Dispatchers.IO) {
        return@withContext try {
            Prefs.getString(SharedPrefsKeys.CURRENCIES_RESPONSE, "")
                ?.takeIf { it.isNotBlank() && it != "{}" }
                ?.let { JSONObject(it) }
        } catch (e: JSONException) {
            null
        }
    }

    suspend fun fetchLastUpdateTime(): DateTime = withContext(Dispatchers.IO) {
        return@withContext DateTime(Prefs.getLong(SharedPrefsKeys.LAST_UPDATED_TIME, 0))
    }

    suspend fun storeCurrenciesResponse(jsonObject: JSONObject) = withContext(Dispatchers.IO) {
        Prefs.putString(SharedPrefsKeys.CURRENCIES_RESPONSE, jsonObject.toString())
    }

    suspend fun updateLastUpdateTimeToNow() = withContext(Dispatchers.IO) {
        Prefs.putLong(SharedPrefsKeys.LAST_UPDATED_TIME, DateTime.now().millis)
    }

}