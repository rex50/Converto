package com.rex50.converto.ui.screens.home

import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rex50.converto.data.repos.open_exchange.OpenExchangeRepo
import com.rex50.converto.data.repos.user.UserSelectionRepo
import com.rex50.converto.ui.models.Currency
import com.rex50.converto.utils.CurrencyConvertor
import com.rex50.converto.utils.CurrencyFormatter
import com.rex50.converto.utils.Data
import com.rex50.converto.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import javax.inject.Inject

@HiltViewModel
class HomeContentViewModel
@Inject
constructor(
    private val openExchangeRepo: OpenExchangeRepo,
    private val userSelectionRepo: UserSelectionRepo,
    private val currencyConvertor: CurrencyConvertor,
    private val currencyFormatter: CurrencyFormatter
) : ViewModel() {

    companion object {
        private const val TAG = "HomeContentViewModel"
    }

    private val _currencies: MutableStateFlow<Data<List<Currency>>> = MutableStateFlow(Data.Loading)
    val currencies = _currencies.asStateFlow()

    private val _selectedFromCurrency = MutableStateFlow(Currency())
    val selectedFromCurrency = _selectedFromCurrency.asStateFlow()

    private val _selectedToCurrency = MutableStateFlow(Currency())
    val selectedToCurrency = _selectedToCurrency.asStateFlow()

    private val _amountToBeConverted = MutableStateFlow("")
    val amountTobeConverted = _amountToBeConverted.asStateFlow()

    private var convertorJob: Job? = null

    init {
        fetchCurrencies()
    }

    fun fetchCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        _currencies.value = Data.Loading
        when (val response = openExchangeRepo.fetchCurrencies()) {
            is Result.Success -> {
                response.data.rates.map {
                    Currency(
                        currency = it.key,
                        rate = it.value
                    )
                }.toMutableList().let { updatedCurrencies ->

                    updateUserLastSessionData(updatedCurrencies)

                    updatedCurrencies.updateConvertedAmount()
                        .sortByCurrency()

                    _currencies.value = Data.Successful(updatedCurrencies)
                }
            }

            is Result.Failure -> {
                _currencies.value = Data.Error("Problem while updating rates.")
            }
        }
    }

    fun convert(text: String) {
        _amountToBeConverted.value = text.removeSpecialCharactersFromAmount()
        _selectedToCurrency.value = _selectedToCurrency.value.also {
            it.updateConversion(
                fromCurrency = _selectedFromCurrency.value,
                amountToBeConverted = _amountToBeConverted.value
            )
        }

        convertorJob?.cancel()
        convertorJob = viewModelScope.launch(Dispatchers.IO) {
            _currencies.value.let { state ->
                if (state is Data.Successful) {
                    try {
                        val updatedCurrencies = state.data.toMutableList()
                            .updateConvertedAmount()
                            .sortByCurrency()
                        _currencies.value = Data.Successful(updatedCurrencies)
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "convert: ", e)
                    }
                }
            }
        }
    }

    /**
     * Changes first currency and recalculates the conversion amount for the second currency
     */
    fun changeSelectedFromCurrency(currency: Currency) = viewModelScope.launch {
        userSelectionRepo.storeSelectedFromCurrency(currency.currency)
        _selectedFromCurrency.value = currency
        _selectedToCurrency.value = _selectedToCurrency.value.also {
            it.updateConversion(
                fromCurrency = currency,
                amountToBeConverted = _amountToBeConverted.value
            )
        }

    }

    /**
     * Changes second currency and recalculates conversion amount
     */
    fun changeSelectedToCurrency(currency: Currency) = viewModelScope.launch {
        userSelectionRepo.storeSelectedToCurrency(currency.currency)
        _selectedToCurrency.value = currency.also {
            it.updateConversion(
                fromCurrency = _selectedFromCurrency.value,
                amountToBeConverted = _amountToBeConverted.value
            )
        }
    }

    /**
     * For formatting currency to display
     */
    fun getFormattedCurrencyAmount(amount: Double?, currencyCode: String): String {
        return currencyFormatter.format(amount, currencyCode)
    }

    fun getFormattedConversionRate(fromCurrency: Currency, toCurrency: Currency): String {
        val conversionRate = currencyConvertor.convertAmount(
            fromCurrency,
            toCurrency,
            amountTobeConverted = 1.0
        )
        val currencyCodeFrom = fromCurrency.currency
        val currencyCodeTo = toCurrency.currency
        return "${getFormattedCurrencyAmount(1.0, currencyCodeFrom)} " +
                currencyCodeFrom +
                " equals ${getFormattedCurrencyAmount(conversionRate, currencyCodeTo)} " +
                currencyCodeTo
    }

    private suspend fun updateUserLastSessionData(updatedCurrencies: MutableList<Currency>) {
        val fromCode = userSelectionRepo.getLastSelectedFromCurrency()
        if(fromCode.isNotBlank())
            updatedCurrencies.find { it.currency == fromCode }?.let {
                changeSelectedFromCurrency(it)
            }

        val toCode = userSelectionRepo.getLastSelectedToCurrency()
        if(toCode.isNotBlank())
            updatedCurrencies.find { it.currency == toCode }?.let {
                changeSelectedToCurrency(it)
            }
    }

    /**
     * Calculates and updates conversion for a given Currency
     */
    private fun Currency.updateConversion(
        fromCurrency: Currency,
        amountToBeConverted: String
    ) {
        convertedCurrency = currencyConvertor.convertAmount(
            fromCurrency = fromCurrency,
            toCurrency = this,
            amountTobeConverted = amountToBeConverted.safelyToDouble()
        )
    }

    /**
     * Calculates and updates conversion for each currency in the list
     */
    private fun MutableList<Currency>.updateConvertedAmount(): MutableList<Currency> {
        forEach { currency ->
            currency.convertedCurrency = currencyConvertor.convertAmount(
                fromCurrency = _selectedFromCurrency.value,
                toCurrency = currency,
                amountTobeConverted = _amountToBeConverted.value.safelyToDouble()
            )
        }
        return this
    }

    /**
     * Sorts the list in place based on currency code
     */
    private fun MutableList<Currency>.sortByCurrency(): List<Currency> {
        sortBy { it.currency }
        return this
    }

    /**
     * Converts from String to Double safely
     */
    private fun String.safelyToDouble(): Double? {
        return try {
            replace(",", "").takeIf {
                it.isNotBlank()
            }?.toDouble()
        } catch (e: NumberFormatException) {
            Log.e(TAG, "formatForConversion: ", e)
            null
        }
    }

    private fun String.removeSpecialCharactersFromAmount(): String {
        return removeInitialNonNumerical()
            .removeSpaceAndHyphen()
            .removeDuplicateDecimal()
    }

    /**
     * Checks and removes spaces and hyphens
     */
    private fun String.removeSpaceAndHyphen(): String {
        return replace("[^0-9\\,\\.]".toRegex(), "")
    }

    /**
     *  Checks and remove duplicate '.' (decimal)
     */
    private fun String.removeDuplicateDecimal(): String {
        return if (count { it == '.' } <= 1)
            this else this.dropLast(1)
    }

    /**
     * Checks and removes selected characters
     */
    private fun String.removeInitialNonNumerical(): String {
        return takeIf { length == 1 }?.replace("[^0-9]".toRegex(), "") ?: this
    }

}
