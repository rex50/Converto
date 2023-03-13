package com.rex50.converto.di

import com.rex50.converto.data.datasources.local.prefs.OpenExchangeLocalDataSourceImpl
import com.rex50.converto.data.datasources.local.prefs.UserSelectionLocalDataSourceImpl
import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.data.repos.open_exchange.OpenExchangeRepo
import com.rex50.converto.data.repos.open_exchange.OpenExchangeRepoImpl
import com.rex50.converto.data.repos.user.UserSelectionRepo
import com.rex50.converto.data.repos.user.UserSelectionRepoImpl
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
        localDataSource: OpenExchangeLocalDataSourceImpl,
        mapper: CurrenciesResponseMapper
    ): OpenExchangeRepo = OpenExchangeRepoImpl(
        remoteDataSource,
        localDataSource,
        mapper
    )


    @Provides
    @Singleton
    fun providesUserSelectionRepo(
        localDataSource: UserSelectionLocalDataSourceImpl
    ): UserSelectionRepo = UserSelectionRepoImpl(localDataSource)

}