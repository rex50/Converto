package com.rex50.converto.di

import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesOpenExchangeService(retrofit: Retrofit) = retrofit.create(OpenExchangeService::class.java)

}