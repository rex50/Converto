package com.rex50.converto.utils

import com.rex50.converto.ui.models.Currency
import junit.framework.TestCase.assertEquals
import org.junit.Test

internal class CurrencyConvertorTest {

    private val currency1 = Currency()
    private val currency2 = Currency(
        currency = "INR",
        rate = 82.0,
        convertedCurrency = null
    )
    private val currency3 = Currency(
        currency = "Yen",
        rate = 133.0,
        convertedCurrency = null
    )
    private val currencyConvertor = CurrencyConvertor()

    @Test
    fun `passing null for amountTobeConverted should return null`() {
        val convertedValue = currencyConvertor.convertAmount(
            currency1,
            currency2,
            null
        )
        assertEquals(null, convertedValue)
    }

    @Test
    fun `passing value for amountTobeConverted should return converted amount`() {
        val convertedValue = currencyConvertor.convertAmount(
            currency1,
            currency2,
            1.0
        )
        assertEquals(82.0, convertedValue)

        val convertedValue2 = currencyConvertor.convertAmount(
            currency2,
            currency1,
            1.0
        )
        assertEquals(0.012195121951219513, convertedValue2)
    }
}