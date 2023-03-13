package com.rex50.converto.utils

import com.rex50.converto.ui.models.Currency
import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class CurrencyFormatterTest {

    private val currency = Currency(convertedCurrency = 1.0)
    private val currencyFormatter = CurrencyFormatter()

    @Test
    fun `after decimal only 2 chars should present`() {
        val formattedCurrency = currencyFormatter.format(currency.convertedCurrency, currency.currency)
        val charsAfterDecimal = formattedCurrency.substringAfter('.')
        assertEquals(2, charsAfterDecimal.length)
    }

    @Test
    fun `formatting should contain symbol and 2 chars after decimal`() {
        val formattedCurrency = currencyFormatter.format(currency.convertedCurrency, currency.currency)
        assertEquals("$1.00", formattedCurrency)
    }

    @Test
    fun `passing null for amount should return zero formatted`() {
        val formattedCurrency = currencyFormatter.format(null, currency.currency)
        assertEquals("$0.00", formattedCurrency)
    }

}