package com.rex50.converto.data.datasources.remote

import com.rex50.converto.BuildConfig
import retrofit2.Retrofit

object OpenExchangeRemoteProvider {

    private const val OPEN_EXCHANGE_BASE_URL = BuildConfig.OPEN_EXCHANGE_SERVICE_URL

    fun build(builder: Retrofit.Builder.() -> Unit): Retrofit {
        return Retrofit.Builder().also {
            it.baseUrl(OPEN_EXCHANGE_BASE_URL)
            builder(it)
        }.build()
    }

}