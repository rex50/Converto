package com.rex50.converto.ui.theme.home

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rex50.converto.data.repos.open_exchange.OpenExchangeRepo
import com.rex50.converto.ui.models.Currency
import com.rex50.converto.utils.CurrencyConvertor
import com.rex50.converto.utils.Data
import com.rex50.converto.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeContentViewModel
@Inject
constructor(
    private val openExchangeRepo: OpenExchangeRepo,
    private val currencyConvertor: CurrencyConvertor
) : ViewModel() {

    companion object {
        private const val TAG = "HomeContentViewModel"
    }

    private val _currencies: MutableStateFlow<Data<List<Currency>>> = MutableStateFlow(Data.Loading)
    val currencies = _currencies.asStateFlow()

    private val _currentCurrency = MutableStateFlow(Currency())
    val currentCurrency = _currentCurrency.asStateFlow()

    private val _amountTobeConverted = MutableStateFlow("")
    val amountTobeConverted = _amountTobeConverted.asStateFlow()

    private var convertorJob: Job? = null

    init {
        fetchCurrencies()
    }

    private fun fetchCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        _currencies.value = Data.Loading
        when (val response = openExchangeRepo.fetchCurrencies()) {
            is Result.Success -> {
                response.data.rates.map {
                    Currency(
                        currency = it.key,
                        rate = it.value
                    )
                }.toMutableList().let { updatedCurrencies ->
                    updatedCurrencies.removeSelectedCurrency()

                    if(_amountTobeConverted.value.isNotBlank()) {
                        updatedCurrencies.updateConvertedAmount()
                    }

                    updatedCurrencies
                        .sortedByCurrency()

                    _currencies.value = Data.Successful(updatedCurrencies)
                }
            }

            is Result.Failure -> {
                _currencies.value = Data.Error("Problem while updating rates.")
            }
        }
    }

    fun convert(text: String) {
        convertorJob?.cancel()
        convertorJob = viewModelScope.launch(Dispatchers.IO) {
            _currencies.value.let { state ->
                if (state is Data.Successful) {
                    try {
                        _amountTobeConverted.value = validateAmount(text)
                        val updatedCurrencies = state.data.toMutableList()
                            .removeSelectedCurrency()
                            .updateConvertedAmount()
                            .sortedByCurrency()
                        _currencies.value = Data.Successful(updatedCurrencies)
                    } catch (e: NumberFormatException) {
                        Log.e(TAG, "convert: ", e)
                    }
                }
            }
        }
    }


    private fun MutableList<Currency>.removeSelectedCurrency(): MutableList<Currency> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            removeIf { it.currency == _currentCurrency.value.currency }
        } else {
            find { it.currency == _currentCurrency.value.currency }?.let {
                remove(it)
            }
        }
        return this
    }

    private fun MutableList<Currency>.updateConvertedAmount(): MutableList<Currency> {
        forEach { currency ->
            currency.convertedCurrency = currencyConvertor.convertAmount(
                fromCurrency = _currentCurrency.value,
                toCurrency = currency,
                amountTobeConverted = formatForConversion(_amountTobeConverted.value)
            )
        }
        return this
    }

    private fun MutableList<Currency>.sortedByCurrency(): List<Currency> {
        sortBy { it.currency }
        return this
    }

    private fun formatForConversion(value: String): Double? {
        return value
            .replace(",", "")
            .takeIf {
                it.isNotBlank()
            }?.toDouble()
    }

    private fun validateAmount(text: String): String {
        return if (text.count { it == '.' } <= 1) text else text.dropLast(1)
    }

}
