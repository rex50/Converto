package com.rex50.converto.data.local.prefs

import android.content.Context
import com.rex50.converto.data.datasources.local.prefs.SharedPrefsKeys
import com.rex50.converto.data.datasources.local.prefs.UserSelectionLocalDataSourceImpl
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UserSelectionLocalDataSourceImplTest: PrefsMockHelper() {

    override val context: Context = mockk(relaxed = true)

    private val localDataSource by lazy {
        UserSelectionLocalDataSourceImpl(context)
    }

    @Test
    fun `storeSelectedFromCurrency stores currencyCode correctly`() = runBlocking {
        mockkEveryPrefsPutString()
        val currencyCode = "INR"

        localDataSource.storeSelectedFromCurrency(currencyCode)

        assertEquals(SharedPrefsKeys.LAST_SELECTED_FROM_CURRENCY, storedString?.first)
        assertEquals(currencyCode, storedString?.second)
    }

    @Test
    fun `getSelectedFromCurrency returns currency code without any error`() = runBlocking {
        val currencyCode = "INR"
        mockkEveryPrefsGetStringReturns(currencyCode)

        val actualCode = localDataSource.getSelectedFromCurrency()

        assertEquals(currencyCode, actualCode)
    }

    @Test
    fun `storeSelectedToCurrency stores currencyCode correctly`() = runBlocking {
        mockkEveryPrefsPutString()
        val currencyCode = "JPY"

        localDataSource.storeSelectedToCurrency(currencyCode)

        assertEquals(SharedPrefsKeys.LAST_SELECTED_TO_CURRENCY, storedString?.first)
        assertEquals(currencyCode, storedString?.second)
    }

    @Test
    fun `getSelectedToCurrency returns currency code without any error`() = runBlocking {
        val currencyCode = "JPY"
        mockkEveryPrefsGetStringReturns(currencyCode)

        val actualCode = localDataSource.getSelectedToCurrency()

        assertEquals(currencyCode, actualCode)
    }
}