package com.rex50.converto.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rex50.converto.data.repos.open_exchange.OpenExchangeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeContentViewModel
@Inject
constructor(
    private val openExchangeRepo: OpenExchangeRepo
) : ViewModel() {

    fun fetchCurrencies() = viewModelScope.launch {
        val response = openExchangeRepo.fetchCurrencies()
    }

}