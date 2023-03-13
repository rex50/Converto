package com.rex50.converto.di

import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.utils.CurrencyConvertor
import com.rex50.converto.utils.CurrencyFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun providesCurrenciesResponseMapper() = CurrenciesResponseMapper()

    @Provides
    @Singleton
    fun providesCurrencyConvertor() = CurrencyConvertor()

    @Singleton
    @Provides
    fun providesCurrencyFormatter() = CurrencyFormatter()

}