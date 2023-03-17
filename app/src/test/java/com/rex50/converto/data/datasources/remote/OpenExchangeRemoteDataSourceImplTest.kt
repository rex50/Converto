package com.rex50.converto.data.datasources.remote

import com.google.gson.JsonElement
import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import com.rex50.converto.utils.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class OpenExchangeRemoteDataSourceImplTest {

    private val mockJsonElement = mockk<JsonElement>(relaxed = true)
    private val service = mockk<OpenExchangeService>()
    private val remoteDataSource = OpenExchangeRemoteDataSourceImpl(service)

    @Test
    fun `fetchCurrenciesRate maps Response of JsonElement to Result of JSONObject`() = runBlocking {
        every { mockJsonElement.toString() } returns "{\"value\":\"Success\"}"
        coEvery { service.fetchCurrencies(any()) } returns Response.success(mockJsonElement)

        val actualResult = remoteDataSource.fetchCurrenciesRate()
        val isSuccessResult = actualResult is Result.Success
        assertEquals(true, isSuccessResult)

        val successData = (actualResult as Result.Success).data
        assertEquals(mockJsonElement.toString(), successData.toString())
    }

    // TODO: Add test for [Response.error] also

}