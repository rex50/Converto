package com.rex50.converto.data.datasources.local.prefs

import android.content.Context
import com.rex50.converto.data.datasources.local.prefs.SharedPrefsKeys
import com.rex50.converto.data.datasources.local.prefs.UserSelectionLocalDataSourceImpl
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class UserSelectionLocalDataSourceImplTest: PrefsMockHelper() {

    override val context: Context = mockk(relaxed = true)

    private val localDataSource by lazy {
        UserSelectionLocalDataSourceImpl(context)
    }

    @Test
    fun `storeSelectedFromCurrency stores currencyCode correctly`() = runTest {
        mockkEveryPrefsPutString()
        val currencyCode = "INR"

        localDataSource.storeSelectedFromCurrency(currencyCode)

        assertEquals(SharedPrefsKeys.LAST_SELECTED_FROM_CURRENCY, storedString?.first)
        assertEquals(currencyCode, storedString?.second)
    }

    @Test
    fun `getSelectedFromCurrency returns currency code without any error`() = runTest {
        val currencyCode = "INR"
        mockkEveryPrefsGetStringReturns(currencyCode)

        val actualCode = localDataSource.getSelectedFromCurrency()

        assertEquals(currencyCode, actualCode)
    }

    @Test
    fun `storeSelectedToCurrency stores currencyCode correctly`() = runTest {
        mockkEveryPrefsPutString()
        val currencyCode = "JPY"

        localDataSource.storeSelectedToCurrency(currencyCode)

        assertEquals(SharedPrefsKeys.LAST_SELECTED_TO_CURRENCY, storedString?.first)
        assertEquals(currencyCode, storedString?.second)
    }

    @Test
    fun `getSelectedToCurrency returns currency code without any error`() = runTest {
        val currencyCode = "JPY"
        mockkEveryPrefsGetStringReturns(currencyCode)

        val actualCode = localDataSource.getSelectedToCurrency()

        assertEquals(currencyCode, actualCode)
    }
}