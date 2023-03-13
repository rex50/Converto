package com.rex50.converto.utils

import com.rex50.converto.ui.models.Currency
import java.text.NumberFormat

class CurrencyFormatter {

    fun format(currency: Currency): String {
        return NumberFormat.getCurrencyInstance().let { currencyFormatter ->
            currencyFormatter.maximumFractionDigits = 2
            currencyFormatter.currency = java.util.Currency.getInstance(currency.currency)
            currency.convertedCurrency?.let { currencyFormatter.format(it) } ?:  "0.0"
        }
    }

}