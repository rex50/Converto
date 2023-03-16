package com.rex50.converto.data.local.prefs

import android.content.Context
import com.rex50.converto.data.datasources.local.prefs.OpenExchangeLocalDataSourceImpl
import com.rex50.converto.data.datasources.local.prefs.SharedPrefsKeys
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import org.json.JSONObject
import org.junit.Test


class OpenExchangeLocalDataSourceImplTest : PrefsMockHelper() {

    override val context = mockk<Context>(relaxed = true)

    private val localDataSource by lazy {
        OpenExchangeLocalDataSourceImpl(context)
    }

    @Test
    fun `When local data is empty fetchCurrenciesResponse returns null`() = runBlocking {
        mockkEveryPrefsGetStringReturns("")
        val response1 = localDataSource.fetchCurrenciesResponse()
        assertEquals(null, response1)

        mockkEveryPrefsGetStringReturns("{}")
        val response2 = localDataSource.fetchCurrenciesResponse()
        assertEquals(null, response2)
    }

    @Test
    fun `When local json data is malformed fetchCurrenciesResponse returns null`() = runBlocking {
        mockkEveryPrefsGetStringReturns("{dsaf:dds:}")
        val response = localDataSource.fetchCurrenciesResponse()
        assertEquals(null, response)
    }

    @Test
    fun `When local json data is present fetchCurrenciesResponse returns JSONObject`() = runBlocking {
        mockkEveryPrefsGetStringReturns("{num: 1}")
        val isJSONObject = localDataSource.fetchCurrenciesResponse() is JSONObject
        assertEquals(true, isJSONObject)
    }

    @Test
    fun `storeCurrenciesResponse converts and stores JSONObject in string`() = runBlocking {
        mockkEveryPrefsPutString()
        val jsonObject = JSONObject("{num: 1}")

        localDataSource.storeCurrenciesResponse(jsonObject)

        assertEquals(SharedPrefsKeys.CURRENCIES_RESPONSE, storedString?.first)
        assertEquals(jsonObject.toString(), storedString?.second)
    }

    @Test
    fun `fetchLastUpdateTime returns DateTime for any long`() = runBlocking {
        initJodaTimeAndroid()
        val millis: Long = 0
        mockkEveryPrefsGetLongReturns(millis)

        assertEquals(DateTime(millis), localDataSource.fetchLastUpdateTime())
    }

    @Test
    fun `updateLastUpdateTimeToNow stores current DateTime millis in long`() = runBlocking {
        initJodaTimeAndroid()
        mockkEveryPrefsPutLong()
        // DateTime constructor for exact millis when now() is called
        mockkConstructor(DateTime::class)
        every { DateTime.now().millis } returns 12345

        localDataSource.updateLastUpdateTimeToNow()

        assertEquals(SharedPrefsKeys.LAST_UPDATED_TIME, storedLong?.first)
        assertEquals(DateTime.now().millis, storedLong?.second)
    }

    /**
     * Joda date time needs to initialized before using DateTime
     */
    private fun initJodaTimeAndroid() {
        JodaTimeAndroid.init(context)
    }
}