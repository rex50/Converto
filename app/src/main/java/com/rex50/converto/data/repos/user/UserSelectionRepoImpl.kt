package com.rex50.converto.data.repos.user

import com.rex50.converto.data.datasources.local.prefs.UserSelectionLocalDataSourceImpl
import javax.inject.Inject

class UserSelectionRepoImpl
@Inject
constructor(
    private val localDataSource: UserSelectionLocalDataSourceImpl
): UserSelectionRepo {
    override suspend fun getLastSelectedFromCurrency(): String {
        return localDataSource.getSelectedFromCurrency()
    }

    override suspend fun storeSelectedFromCurrency(currencyCode: String) {
        localDataSource.storeSelectedFromCurrency(currencyCode)
    }

    override suspend fun getLastSelectedToCurrency(): String {
        return localDataSource.getSelectedToCurrency()
    }

    override suspend fun storeSelectedToCurrency(currencyCode: String) {
        localDataSource.storeSelectedToCurrency(currencyCode)
    }
}