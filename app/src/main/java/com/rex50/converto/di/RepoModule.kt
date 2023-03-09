package com.rex50.converto.di

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
    fun providesOpenExchangeRepo(openExchangeService: OpenExchangeService): OpenExchangeRepo =
        OpenExchangeRepoImpl(openExchangeService)

}