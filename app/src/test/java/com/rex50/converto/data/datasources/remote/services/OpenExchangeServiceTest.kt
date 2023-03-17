package com.rex50.converto.data.datasources.remote.services

import com.rex50.converto.BuildConfig
import com.rex50.converto.di.NetworkModule
import com.rex50.converto.di.ServiceModule
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test

class OpenExchangeServiceTest {

    private val service by lazy {
        ServiceModule.providesOpenExchangeService(
            NetworkModule.providesOpenExchangeRetrofit(
                NetworkModule.provideGson(),
                NetworkModule.provideOkHttpClient(
                    NetworkModule.providesLoggingInterceptor()
                )
            )
        )
    }

    //@Test
    fun `call fetch currencies api`() = runBlocking {
        val response = service.fetchCurrencies(BuildConfig.OPEN_EXCHANGE_SERVICE_KEY)
        assertNotNull(response)
        assertTrue(response.isSuccessful)
    }

}