package com.rex50.converto.utils

import java.text.DecimalFormat
import java.util.*

class CurrencyFormatter {

    fun format(amount: Double?, currencyCode: String): String =
        DecimalFormat.getCurrencyInstance().let { currencyFormatter ->
            val (currency, symbol) = getCurrency(currencyCode)
            currencyFormatter.currency = currency
            currencyFormatter.maximumFractionDigits = 2
            amount?.let {
                val formattedValue = currencyFormatter.format(it)

                // After 18 digits, accuracy decreases so use scientific expression
                if (formattedValue.replace(symbol, "").length >= 18)
                    "$symbol$amount"
                else
                    formattedValue.replace(currencyCode, "")
            } ?: "${symbol}0.00"
        }

    private fun getCurrency(currencyCode: String): Pair<Currency, String> =
        Currency.getInstance(currencyCode).let {
            it to it.symbol.replace(currencyCode, "")
        }
}