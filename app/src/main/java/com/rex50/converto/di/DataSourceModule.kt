package com.rex50.converto.di

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
    fun providesOpenExchangeDataSource(openExchangeService: OpenExchangeService) =
        OpenExchangeRemoteDataSourceImpl(openExchangeService)

}