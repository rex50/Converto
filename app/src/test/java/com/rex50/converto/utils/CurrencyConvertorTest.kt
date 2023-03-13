package com.rex50.converto.utils

import com.rex50.converto.ui.models.Currency
import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class CurrencyConvertorTest {

    private val fromCurrency = Currency()
    private val toCurrency = Currency(
        currency = "INR",
        rate = 82.0,
        convertedCurrency = null
    )
    private val currencyConvertor = CurrencyConvertor()

    @Test
    fun `passing null for amountTobeConverted should return null`() {
        val convertedValue = currencyConvertor.convertAmount(
            fromCurrency,
            toCurrency,
            null
        )
        assertEquals(convertedValue, null)
    }

    @Test
    fun `passing value for amountTobeConverted should return converted amount`() {
        val convertedValue = currencyConvertor.convertAmount(
            fromCurrency,
            toCurrency,
            1.0
        )
        assertEquals(convertedValue, 82.0)
    }
}