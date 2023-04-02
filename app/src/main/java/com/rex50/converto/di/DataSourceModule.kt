package com.rex50.converto.di

import androidx.datastore.core.DataStore
import com.rex50.converto.data.datasources.local.OpenExchangeLocalDataSourceImpl
import com.rex50.converto.data.datasources.local.UserSelectionLocalDataSourceImpl
import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import com.rex50.converto.data.models.OpenExchangeData
import com.rex50.converto.data.models.UserLastSessionData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesOpenExchangeRemoteDataSource(openExchangeService: OpenExchangeService) =
        OpenExchangeRemoteDataSourceImpl(openExchangeService)

    @Singleton
    @Provides
    fun providesOpenExchangeLocalDataSource(openExchangeDataStore: DataStore<OpenExchangeData>) =
        OpenExchangeLocalDataSourceImpl(openExchangeDataStore)


    @Singleton
    @Provides
    fun providesUserSelectionLocalDataSource(userLastSessionDataStore: DataStore<UserLastSessionData>) =
        UserSelectionLocalDataSourceImpl(userLastSessionDataStore)

}