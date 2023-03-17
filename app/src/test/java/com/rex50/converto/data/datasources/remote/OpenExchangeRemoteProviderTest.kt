package com.rex50.converto.data.datasources.remote

import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteProvider
import junit.framework.TestCase.*
import org.junit.Test
import retrofit2.Retrofit

class OpenExchangeRemoteProviderTest {

    @Test
    fun `provider provides Retrofit instance`() {
        val retrofit = OpenExchangeRemoteProvider.build {  }
        assertNotNull(retrofit)
        assertTrue(retrofit.baseUrl().toString().isNotBlank())
    }

}