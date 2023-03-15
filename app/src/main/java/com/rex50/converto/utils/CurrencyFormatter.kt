package com.rex50.converto.utils

import java.text.DecimalFormat

class CurrencyFormatter {

    fun format(amount: Double?, currencyCode: String): String {
        return DecimalFormat.getCurrencyInstance().let { currencyFormatter ->
            currencyFormatter.maximumFractionDigits = 2
            var symbol: String
            java.util.Currency.getInstance(currencyCode).let {
                currencyFormatter.currency = it
                symbol = it.symbol
            }
            amount?.let {
                currencyFormatter.format(it)
                    .replace(currencyCode, "")
            } ?: "${symbol}0.00"
        }
    }

}