package com.rex50.converto.data.repos.open_exchange

import android.content.Context
import com.rex50.converto.TestUtils.countRatesCount
import com.rex50.converto.TestUtils.initJodaTimeAndroid
import com.rex50.converto.data.MockResponses
import com.rex50.converto.data.datasources.local.prefs.OpenExchangeLocalDataSourceImpl
import com.rex50.converto.data.datasources.remote.OpenExchangeRemoteDataSourceImpl
import com.rex50.converto.data.datasources.remote.mappers.CurrenciesResponseMapper
import com.rex50.converto.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test

class OpenExchangeRepoImplTest {

    private val context = mockk<Context>(relaxed = true)
    private val remoteDataSource = mockk<OpenExchangeRemoteDataSourceImpl>()
    private val localDataSource = mockk<OpenExchangeLocalDataSourceImpl>(relaxed = true)

    private val localDataSourceResponse = JSONObject(MockResponses.successStringWith3Rates)
    private val localDataSourceRateListSize = countRatesCount(localDataSourceResponse)
    private val remoteDataSourceResponse = JSONObject(MockResponses.successStringWith5Rates)
    private val remoteDataSourceRateListSize = countRatesCount(remoteDataSourceResponse)


    private val repo = OpenExchangeRepoImpl(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource,
        responseMapper = CurrenciesResponseMapper()
    )

    @Before
    fun `mock and stub`() {
        initJodaTimeAndroid(context)

        coEvery { localDataSource.fetchCurrenciesResponse() } returns localDataSourceResponse
        coEvery { localDataSource.fetchLastUpdateTime() } returns DateTime.now()
        coEvery { remoteDataSource.fetchCurrenciesRate() } returns Result.Success(
            remoteDataSourceResponse
        )
    }

    @Test
    fun `fetchCurrencies returns local data when available and not expired`() = runBlocking {
        val result = repo.fetchCurrencies()

        coVerify { localDataSource.fetchCurrenciesResponse() }
        coVerify(inverse = true) { remoteDataSource.fetchCurrenciesRate() }
        assertTrue(result is Result.Success)
        assertTrue(localDataSourceRateListSize == (result as Result.Success).data.rates.size)
    }

    @Test
    fun `fetchCurrencies returns remote data when local data is not available`() = runBlocking {
        coEvery { localDataSource.fetchCurrenciesResponse() } returns null
        val result = repo.fetchCurrencies()

        coVerify { remoteDataSource.fetchCurrenciesRate() }
        assertTrue(result is Result.Success)
        assertTrue(remoteDataSourceRateListSize == (result as Result.Success).data.rates.size)
    }

    @Test
    fun `fetchCurrencies returns remote data when local data is expired`() = runBlocking {
        coEvery { localDataSource.fetchLastUpdateTime() } returns DateTime.now().minusHours(4)
        val result = repo.fetchCurrencies()

        coVerify { remoteDataSource.fetchCurrenciesRate() }
        assertTrue(result is Result.Success)
        assertTrue(remoteDataSourceRateListSize == (result as Result.Success).data.rates.size)
    }

    @After
    fun clean(){
        unmockkAll()
    }

}