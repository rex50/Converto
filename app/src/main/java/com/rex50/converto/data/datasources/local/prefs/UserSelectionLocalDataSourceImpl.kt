package com.rex50.converto.data.datasources.local.prefs

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserSelectionLocalDataSourceImpl
@Inject
constructor(context: Context): Prefs(context) {

    suspend fun storeSelectedFromCurrency(currencyCode: String) = withContext(Dispatchers.IO) {
        SharedPrefsKeys.LAST_SELECTED_FROM_CURRENCY.put(currencyCode)
    }

    suspend fun getSelectedFromCurrency(): String = withContext(Dispatchers.IO) {
        return@withContext SharedPrefsKeys.LAST_SELECTED_FROM_CURRENCY.getString("INR")
    }

    suspend fun storeSelectedToCurrency(currencyCode: String) = withContext(Dispatchers.IO) {
        SharedPrefsKeys.LAST_SELECTED_TO_CURRENCY.put(currencyCode)
    }

    suspend fun getSelectedToCurrency(): String = withContext(Dispatchers.IO) {
        return@withContext SharedPrefsKeys.LAST_SELECTED_TO_CURRENCY.getString("JPY")
    }

}