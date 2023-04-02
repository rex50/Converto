package com.rex50.converto.di

import android.content.Context
import com.rex50.converto.data.datasources.local.datastore.DataStores
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesUserLastSessionDataStore(@ApplicationContext context: Context) =
        DataStores.userLastSessionDataStore(context)

    @Provides
    @Singleton
    fun providesOpenExchangeDataStore(@ApplicationContext context: Context) =
        DataStores.openExchangeDataStore(context)

}
