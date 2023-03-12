package com.rex50.converto.di

import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import com.rex50.converto.data.repos.open_exchange.OpenExchangeRepo
import com.rex50.converto.data.repos.open_exchange.OpenExchangeRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun providesOpenExchangeRepo(
        remoteDataSource: OpenExchangeRemoteDataSourceImpl,
        mapper: CurrenciesResponseMapper
    ): OpenExchangeRepo = OpenExchangeRepoImpl(
        remoteDataSource,
        mapper
    )

}