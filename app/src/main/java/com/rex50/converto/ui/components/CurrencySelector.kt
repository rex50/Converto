package com.rex50.converto.ui.components

import androidx.compose.runtime.Composable
import com.rex50.converto.ui.models.Currency

@Composable
fun CurrencySelector(
    selectedCurrency: Currency,
    currencies: List<Currency>,
    onCurrencySelected: (Currency) -> Unit,
    onDismissRequest: () -> Unit
) {
    BottomSheetMenu(
        notSetLabel = "Select a currency",
        items = currencies,
        selectedIndex = currencies.indexOf(selectedCurrency),
        onItemSelected = { _, item ->
            onCurrencySelected(item)
        },
        onDismissRequest = onDismissRequest,
        drawItem = { item, selected, itemEnabled, onClick ->
            BottomSheetMenuItem(
                text = "${item.country} (${item.currency})",
                selected = selected,
                enabled = itemEnabled,
                onClick = onClick,
            )
        }
    )
}