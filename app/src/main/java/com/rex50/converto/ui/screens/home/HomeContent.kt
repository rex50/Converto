package com.rex50.converto.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun HomeContent(
    viewModel: HomeContentViewModel = hiltViewModel()
) {
    Box {
        Button(
            onClick = { viewModel.fetchCurrencies() }
        ) {
            Text(text = "Update Currencies")
        }
    }
}