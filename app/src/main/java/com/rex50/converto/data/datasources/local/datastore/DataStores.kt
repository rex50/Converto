package com.rex50.converto.data.datasources.local.datastore

import android.content.Context
import androidx.datastore.dataStore

object DataStores {

    object FileNames {
        const val userLastSessionDataStore = "user-last-session.json"
        const val openExchangeDataStore = "open-exchange.json"
    }

    object Serializers {
        val userLastSessionDataStore = UserLastSessionDataSerializer
        val openExchangeDataStore = OpenExchangeDataSerializer
    }

    private val Context.userLastSessionDataStore by dataStore(
        fileName = FileNames.userLastSessionDataStore,
        serializer = Serializers.userLastSessionDataStore
    )
    private val Context.openExchangeDataStore by dataStore(
        fileName = FileNames.openExchangeDataStore,
        serializer = Serializers.openExchangeDataStore
    )

    fun userLastSessionDataStore(context: Context) = context.userLastSessionDataStore
    fun openExchangeDataStore(context: Context) = context.openExchangeDataStore
}