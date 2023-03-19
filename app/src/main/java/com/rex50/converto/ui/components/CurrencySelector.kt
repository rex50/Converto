package com.rex50.converto.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rex50.converto.R
import com.rex50.converto.ui.components.core.BottomSheetMenu
import com.rex50.converto.ui.components.core.BottomSheetMenuItem
import com.rex50.converto.ui.models.Currency

@Composable
fun CurrencySelector(
    selectedCurrency: Currency,
    currencies: List<Currency>,
    onCurrencySelected: (Currency) -> Unit,
    onDismissRequest: () -> Unit
) {

   // TODO: Show search bar
    BottomSheetMenu(
        notSetLabel = stringResource(R.string.select_a_currency),
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