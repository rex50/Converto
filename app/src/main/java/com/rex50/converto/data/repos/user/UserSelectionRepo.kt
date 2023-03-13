package com.rex50.converto.data.repos.user

interface UserSelectionRepo {
    suspend fun getLastSelectedFromCurrency(): String

    suspend fun storeSelectedFromCurrency(currencyCode: String)

    suspend fun getLastSelectedToCurrency(): String

    suspend fun storeSelectedToCurrency(currencyCode: String)
}