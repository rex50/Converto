package com.rex50.converto.data.datasources.remote

import com.google.gson.JsonElement
import com.rex50.converto.data.datasources.remote.services.OpenExchangeService
import com.rex50.converto.utils.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class OpenExchangeRemoteDataSourceImplTest {

    private val mockJsonElement = mockk<JsonElement>(relaxed = true)
    private val service = mockk<OpenExchangeService>()
    private val remoteDataSource = OpenExchangeRemoteDataSourceImpl(service)

    @Test
    fun `fetchCurrenciesRate maps Response of JsonElement to Result of JSONObject`() = runTest {
        every { mockJsonElement.toString() } returns "{\"value\":\"Success\"}"
        coEvery { service.fetchLatestRates(any()) } returns Response.success(mockJsonElement)

        val actualResult = remoteDataSource.fetchCurrenciesRate()
        val isSuccessResult = actualResult is Result.Success
        assertEquals(true, isSuccessResult)

        val successData = (actualResult as Result.Success).data
        assertEquals(mockJsonElement.toString(), successData.toString())
    }

    // TODO: Add test for [Response.error] also

}