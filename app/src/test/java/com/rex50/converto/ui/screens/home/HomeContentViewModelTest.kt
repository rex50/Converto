package com.rex50.converto.ui.screens.home

import android.util.Log
import com.rex50.converto.MainDispatcherRule
import com.rex50.converto.TestUtils.createCurrenciesRateResponse
import com.rex50.converto.TestUtils.mockLogs
import com.rex50.converto.awaitCompletion
import com.rex50.converto.data.repos.open_exchange.OpenExchangeRepo
import com.rex50.converto.data.repos.user.UserSelectionRepo
import com.rex50.converto.ui.models.Currency
import com.rex50.converto.utils.CurrencyConvertor
import com.rex50.converto.utils.CurrencyFormatter
import com.rex50.converto.utils.Data
import com.rex50.converto.utils.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeContentViewModelTest {

    private val openExchangeRepo = mockk<OpenExchangeRepo>(relaxed = true)
    private val userSelectionRepo = mockk<UserSelectionRepo>(relaxed = true)
    private val currencyConvertor = mockk<CurrencyConvertor>(relaxed = true)
    private val currencyFormatter = mockk<CurrencyFormatter>(relaxed = true)
    private val viewModel by lazy {
        HomeContentViewModel(
            openExchangeRepo = openExchangeRepo,
            userSelectionRepo = userSelectionRepo,
            currencyConvertor = currencyConvertor,
            currencyFormatter = currencyFormatter
        )
    }

    private val log = mockk<Log>(relaxed = true)
    private val successResponse = createCurrenciesRateResponse()
    private val lastSelectedFromCurrency = "AED"
    private val lastSelectedToCurrency = "AFN"

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun init() {
        coEvery { openExchangeRepo.fetchCurrencies() } returns Result.Success(successResponse)
        coEvery { userSelectionRepo.getLastSelectedFromCurrency() } returns lastSelectedFromCurrency
        coEvery { userSelectionRepo.getLastSelectedToCurrency() } returns lastSelectedToCurrency
        every { currencyFormatter.format(any(), any()) } answers {
            if(secondArg<String>() == "USD") "$1.00" else "₹20.00"
        }
        every { currencyConvertor.convertAmount(any(), any(), any()) } returns 20.0

        mockLogs()
    }

    @Test
    fun `fetchCurrencies success, updates all currencies and last selected currencies`() = runTest {
        val job = viewModel.fetchCurrencies()
        job.awaitCompletion()

        assertTrue(viewModel.currencies.value is Data.Successful)
        assertTrue((viewModel.currencies.value as Data.Successful).data.isNotEmpty())

        assertTrue(viewModel.selectedFromCurrency.value.currency == lastSelectedFromCurrency)
        assertTrue(viewModel.selectedToCurrency.value.currency == lastSelectedToCurrency)
    }

    @Test
    fun `fetchCurrencies failure, sets error in currencies`() = runTest {
        coEvery { openExchangeRepo.fetchCurrencies() } returns Result.Failure(Exception("Test"))

        val job = viewModel.fetchCurrencies()
        job.awaitCompletion()

        assertTrue(viewModel.currencies.value is Data.Error)
    }

    @Test
    fun `calling convert with valid input, updates amountToBeConverted`() = runTest {
        // Before calling convert, verify that amountTobeConverted is empty
        assertTrue(viewModel.amountTobeConverted.value.isEmpty())

        val expected = "1"

        viewModel.convert(expected)
        // Verify amountTobeConverted is not empty anymore
        assertEquals(expected, viewModel.amountTobeConverted.value)
    }

    @Test
    fun `calling convert with invalid input, doesn't update amountToBeConverted`() = runTest {
        // Before calling convert, verify that amountTobeConverted is empty
        assertTrue(viewModel.amountTobeConverted.value.isEmpty())

        viewModel.convert("#&")
        // Verify amountTobeConverted is not empty anymore
        assertEquals("", viewModel.amountTobeConverted.value)
    }

    @Test
    fun `calling convert with valid input, updates amountToBeConverted and all currencies conversions`() =
        runTest {
            // fetch currencies
            val job = viewModel.fetchCurrencies()
            job.awaitCompletion()

            // Before calling convert, verify that amountTobeConverted is empty
            assertTrue(viewModel.amountTobeConverted.value.isEmpty())

            viewModel.convert("1")
            // Verify amountTobeConverted is not empty anymore
            assertTrue(viewModel.amountTobeConverted.value.isNotEmpty())
            //Verify currencies convertedCurrency (conversion) is not null
            assertTrue((viewModel.currencies.value as Data.Successful).data.first().convertedCurrency != null)
        }

    @Test
    fun `getFormattedCurrencyAmount returns formatted amount to display`() = runTest {
        val formattedAmount = viewModel.getFormattedCurrencyAmount(1.0, "USD")
        assertEquals("$1.00", formattedAmount)
    }

    @Test
    fun `getFormattedConversionRate returns formatted conversion rate`() = runTest {
        val formattedRate = viewModel.getFormattedConversionRate(Currency(), Currency(currency = "INR"))
        assertEquals("$1.00 USD equals ₹20.00 INR", formattedRate)
    }

}