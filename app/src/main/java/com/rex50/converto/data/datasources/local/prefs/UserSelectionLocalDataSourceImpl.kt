package com.rex50.converto.data.datasources.local.prefs

import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserSelectionLocalDataSourceImpl {

    suspend fun storeSelectedFromCurrency(currencyCode: String) = withContext(Dispatchers.IO) {
        Prefs.putString(SharedPrefsKeys.LAST_SELECTED_FROM_CURRENCY, currencyCode)
    }

    suspend fun getSelectedFromCurrency(): String = withContext(Dispatchers.IO) {
        return@withContext Prefs.getString(SharedPrefsKeys.LAST_SELECTED_FROM_CURRENCY, "")
    }

    suspend fun storeSelectedToCurrency(currencyCode: String) = withContext(Dispatchers.IO) {
        Prefs.putString(SharedPrefsKeys.LAST_SELECTED_TO_CURRENCY, currencyCode)
    }

    suspend fun getSelectedToCurrency(): String = withContext(Dispatchers.IO) {
        return@withContext Prefs.getString(SharedPrefsKeys.LAST_SELECTED_TO_CURRENCY, "")
    }

}