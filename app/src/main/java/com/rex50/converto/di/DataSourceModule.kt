package com.rex50.converto.di

import com.rex50.converto.data.datasources.local.prefs.OpenExchangeLocalDataSourceImpl
import com.rex50.converto.data.datasources.local.prefs.UserSelectionLocalDataSourceImpl
import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
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
    fun providesOpenExchangeLocalDataSource() = OpenExchangeLocalDataSourceImpl()


    @Singleton
    @Provides
    fun providesUserSelectionLocalDataSource() = UserSelectionLocalDataSourceImpl()

}