package com.rex50.converto.di

import android.content.Context
import com.rex50.converto.data.datasources.local.prefs.OpenExchangeLocalDataSourceImpl
import com.rex50.converto.data.datasources.local.prefs.UserSelectionLocalDataSourceImpl
import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesOpenExchangeLocalDataSource(@ApplicationContext context: Context) = OpenExchangeLocalDataSourceImpl(context)


    @Singleton
    @Provides
    fun providesUserSelectionLocalDataSource(@ApplicationContext context: Context) = UserSelectionLocalDataSourceImpl(context)

}