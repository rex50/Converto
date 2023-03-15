package com.rex50.converto.utils

import com.rex50.converto.ui.models.Currency

class CurrencyConvertor {

    private val base = "USD"

    fun convertAmount(
        fromCurrency: Currency,
        toCurrency: Currency,
        amountTobeConverted: Double?
    ): Double? {
        return amountTobeConverted?.let {
            val rate = if(fromCurrency.currency.equals(base, ignoreCase = true))
                fromCurrency.rate * toCurrency.rate
            else
                toCurrency.rate / fromCurrency.rate

            return rate*it
        }
    }

}