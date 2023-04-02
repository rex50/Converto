package com.rex50.converto.data.datasources.local

import androidx.datastore.core.DataStore
import com.rex50.converto.data.models.UserLastSessionData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserSelectionLocalDataSourceImpl
@Inject
constructor(private val userLastSessionDataStore: DataStore<UserLastSessionData>) {

    suspend fun storeSelectedFromCurrency(currencyCode: String) = withContext(Dispatchers.IO) {
        userLastSessionDataStore.updateData {
            it.copy(fromCurrencyCode = currencyCode)
        }
    }

    suspend fun getSelectedFromCurrency(): String = withContext(Dispatchers.IO) {
        return@withContext userLastSessionDataStore.data.first().fromCurrencyCode
    }

    suspend fun storeSelectedToCurrency(currencyCode: String) = withContext(Dispatchers.IO) {
        userLastSessionDataStore.updateData {
            it.copy(toCurrencyCode = currencyCode)
        }
    }

    suspend fun getSelectedToCurrency(): String = withContext(Dispatchers.IO) {
        return@withContext userLastSessionDataStore.data.first().toCurrencyCode
    }

}